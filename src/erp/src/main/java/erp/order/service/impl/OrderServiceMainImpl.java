package erp.order.service.impl;

import erp.order.dto.OrderCreateRequestDto;
import erp.order.dto.OrderCreateResponseDto;
import erp.order.dto.OrderItemResponseDto;
import erp.order.dto.OrderUpdateRequestDto;
import erp.order.dto.OrderUpdateResponseDto;
import erp.order.dto.OrderStatusUpdateDto;
import erp.order.dto.OrderItemUpdateDto;
import erp.order.entity.Order;
import erp.order.entity.OrderItem;
import erp.order.enums.OrderStatus;
import erp.order.service.OrderItemService;
import erp.order.service.OrderService;
import erp.payment.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 訂單服務主實現類
 * 協調訂單查詢和創建更新功能模塊的實現
 */
@Service
@RequiredArgsConstructor
public class OrderServiceMainImpl implements OrderService {
    
    private final OrderQueryServiceImpl queryService;
    private final OrderCreateUpdateServiceImpl createUpdateService;
    private final OrderItemService orderItemService;
    
    //region 訂單查詢相關方法委派 (Read)
    @Override
    public Order getOrderById(Long id) {
        return queryService.getOrderById(id);
    }
    
    @Override
    public Order getOrderByNo(String orderNo) {
        return queryService.getOrderByNo(orderNo);
    }
    
    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return queryService.getAllOrders(pageable);
    }
    
    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return queryService.getOrdersByCustomerId(customerId);
    }
    
    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return queryService.getOrdersByStatus(status);
    }
    
    @Override
    public List<Order> getOrdersByPaymentMethod(PaymentMethod paymentMethod) {
        return queryService.getOrdersByPaymentMethod(paymentMethod);
    }
    
    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return queryService.getOrdersByDateRange(startDate, endDate);
    }
    
    @Override
    public List<Order> getOrdersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return queryService.getOrdersByAmountRange(minAmount, maxAmount);
    }
    //endregion
    
    //region 訂單操作相關方法委派 (Create & Update)
    @Override
    public Order createOrder(Long customerId, OrderStatus status, BigDecimal totalAmount, 
                           BigDecimal discountAmount, BigDecimal finalAmount, 
                           PaymentMethod paymentMethod, String notes) {
        return createUpdateService.createOrder(customerId, status, totalAmount, discountAmount, 
                                             finalAmount, paymentMethod, notes);
    }
    
    @Override
    public Order updateOrder(Long orderId, Long customerId, OrderStatus status, 
                           BigDecimal totalAmount, BigDecimal discountAmount, 
                           BigDecimal finalAmount, PaymentMethod paymentMethod, String notes) {
        return createUpdateService.updateOrder(orderId, customerId, status, totalAmount, 
                                             discountAmount, finalAmount, paymentMethod, notes);
    }
    
    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        return createUpdateService.updateOrderStatus(orderId, status);
    }
    //endregion
    
    //region 控制器專用業務邏輯區塊 - 創建相關
    @Override
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto request) {
        // 計算訂單總額
        BigDecimal totalAmount = request.getOrderItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 計算實付金額
        BigDecimal discountAmount = request.getDiscountAmount() != null ? request.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal finalAmount = totalAmount.subtract(discountAmount);
        
        // 設置預設值
        OrderStatus status = request.getStatus() != null ? request.getStatus() : OrderStatus.PENDING;
        LocalDateTime orderTime = request.getOrderTime() != null ? request.getOrderTime() : LocalDateTime.now();
        
        // 創建訂單
        Order createdOrder = createUpdateService.createOrder(
                request.getCustomerId(), 
                status, 
                totalAmount, 
                discountAmount, 
                finalAmount, 
                request.getPaymentMethod(), 
                request.getNotes()
        );
        
        // 創建訂單明細
        List<OrderItemResponseDto> orderItemResponses = request.getOrderItems().stream()
                .map(itemDto -> {
                    OrderItem orderItem = orderItemService.createOrderItem(
                            createdOrder.getId(),
                            itemDto.getProductId(),
                            itemDto.getProductName(),
                            itemDto.getProductType(),
                            itemDto.getQty(),
                            itemDto.getUnitPrice(),
                            itemDto.getNotes()
                    );
                    
                    return OrderItemResponseDto.builder()
                            .id(orderItem.getId())
                            .productId(orderItem.getProductId())
                            .productName(orderItem.getProductName())
                            .productType(orderItem.getProductType())
                            .qty(orderItem.getQty())
                            .unitPrice(orderItem.getUnitPrice())
                            .subtotal(orderItem.getSubtotal())
                            .notes(orderItem.getNotes())
                            .createdAt(orderItem.getCreatedAt())
                            .build();
                })
                .toList();
        
        // 構建回應DTO
        return OrderCreateResponseDto.builder()
                .id(createdOrder.getId())
                .no(createdOrder.getNo())
                .customerId(createdOrder.getCustomerId())
                .orderTime(createdOrder.getOrderTime())
                .status(createdOrder.getStatus())
                .totalAmount(createdOrder.getTotalAmount())
                .discountAmount(createdOrder.getDiscountAmount())
                .finalAmount(createdOrder.getFinalAmount())
                .paymentMethod(createdOrder.getPaymentMethod())
                .notes(createdOrder.getNotes())
                .createdAt(createdOrder.getCreatedAt())
                .orderItems(orderItemResponses)
                .build();
    }
    //endregion
    
    //region 控制器專用業務邏輯區塊 - 更新相關
    @Override
    public OrderUpdateResponseDto updateOrder(OrderUpdateRequestDto request) {
        // 獲取現有訂單
        Order existingOrder = queryService.getOrderById(request.getId());
        
        // 計算訂單總額（如果有訂單項目更新）
        BigDecimal totalAmount;
        if (request.getOrderItems() != null && !request.getOrderItems().isEmpty()) {
            totalAmount = request.getOrderItems().stream()
                    .filter(item -> !"DELETE".equals(item.getAction()))
                    .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQty())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            totalAmount = existingOrder.getTotalAmount();
        }
        
        // 計算實付金額
        BigDecimal discountAmount = request.getDiscountAmount() != null ? 
                request.getDiscountAmount() : existingOrder.getDiscountAmount();
        BigDecimal finalAmount = totalAmount.subtract(discountAmount);
        
        // 使用請求中的值或保持原有值
        Long customerId = request.getCustomerId() != null ? 
                request.getCustomerId() : existingOrder.getCustomerId();
        OrderStatus status = request.getStatus() != null ? 
                request.getStatus() : existingOrder.getStatus();
        PaymentMethod paymentMethod = request.getPaymentMethod() != null ? 
                request.getPaymentMethod() : existingOrder.getPaymentMethod();
        String notes = request.getNotes() != null ? 
                request.getNotes() : existingOrder.getNotes();
        
        // 更新訂單
        Order updatedOrder = createUpdateService.updateOrder(
                request.getId(),
                customerId,
                status,
                totalAmount,
                discountAmount,
                finalAmount,
                paymentMethod,
                notes
        );
        
        // 處理訂單項目更新
        List<OrderItemResponseDto> orderItemResponses;
        if (request.getOrderItems() != null && !request.getOrderItems().isEmpty()) {
            orderItemResponses = processOrderItemUpdates(request.getId(), request.getOrderItems());
        } else {
            // 如果沒有項目更新，獲取現有項目
            List<OrderItem> existingItems = orderItemService.getOrderItemsByOrderId(request.getId());
            orderItemResponses = existingItems.stream()
                    .map(item -> OrderItemResponseDto.builder()
                            .id(item.getId())
                            .productId(item.getProductId())
                            .productName(item.getProductName())
                            .productType(item.getProductType())
                            .qty(item.getQty())
                            .unitPrice(item.getUnitPrice())
                            .subtotal(item.getSubtotal())
                            .notes(item.getNotes())
                            .createdAt(item.getCreatedAt())
                            .build())
                    .toList();
        }
        
        // 構建回應DTO
        return OrderUpdateResponseDto.builder()
                .id(updatedOrder.getId())
                .no(updatedOrder.getNo())
                .customerId(updatedOrder.getCustomerId())
                .orderTime(updatedOrder.getOrderTime())
                .status(updatedOrder.getStatus())
                .totalAmount(updatedOrder.getTotalAmount())
                .discountAmount(updatedOrder.getDiscountAmount())
                .finalAmount(updatedOrder.getFinalAmount())
                .paymentMethod(updatedOrder.getPaymentMethod())
                .notes(updatedOrder.getNotes())
                .updatedAt(updatedOrder.getUpdatedAt())
                .orderItems(orderItemResponses)
                .build();
    }
    
    @Override
    public OrderUpdateResponseDto updateOrderStatus(OrderStatusUpdateDto request) {
        // 更新訂單狀態
        Order updatedOrder = createUpdateService.updateOrderStatus(request.getId(), request.getStatus());
        
        // 獲取現有訂單項目
        List<OrderItem> existingItems = orderItemService.getOrderItemsByOrderId(request.getId());
        List<OrderItemResponseDto> orderItemResponses = existingItems.stream()
                .map(item -> OrderItemResponseDto.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .productType(item.getProductType())
                        .qty(item.getQty())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .notes(item.getNotes())
                        .createdAt(item.getCreatedAt())
                        .build())
                .toList();
        
        // 構建回應DTO
        return OrderUpdateResponseDto.builder()
                .id(updatedOrder.getId())
                .no(updatedOrder.getNo())
                .customerId(updatedOrder.getCustomerId())
                .orderTime(updatedOrder.getOrderTime())
                .status(updatedOrder.getStatus())
                .totalAmount(updatedOrder.getTotalAmount())
                .discountAmount(updatedOrder.getDiscountAmount())
                .finalAmount(updatedOrder.getFinalAmount())
                .paymentMethod(updatedOrder.getPaymentMethod())
                .notes(updatedOrder.getNotes())
                .updatedAt(updatedOrder.getUpdatedAt())
                .orderItems(orderItemResponses)
                .build();
    }
    
    /**
     * 處理訂單項目更新
     */
    private List<OrderItemResponseDto> processOrderItemUpdates(Long orderId, List<OrderItemUpdateDto> itemUpdates) {
        return itemUpdates.stream()
                .map(itemDto -> {
                    OrderItem orderItem;
                    switch (itemDto.getAction()) {
                        case "ADD":
                            orderItem = orderItemService.createOrderItem(
                                    orderId,
                                    itemDto.getProductId(),
                                    itemDto.getProductName(),
                                    itemDto.getProductType(),
                                    itemDto.getQty(),
                                    itemDto.getUnitPrice(),
                                    itemDto.getNotes()
                            );
                            break;
                        case "UPDATE":
                            orderItem = orderItemService.updateOrderItem(
                                    itemDto.getId(),
                                    itemDto.getProductId(),
                                    itemDto.getProductName(),
                                    itemDto.getProductType(),
                                    itemDto.getQty(),
                                    itemDto.getUnitPrice(),
                                    itemDto.getNotes()
                            );
                            break;
                        case "DELETE":
                            orderItemService.deleteOrderItem(itemDto.getId());
                            return null; // 刪除的項目不返回
                        default:
                            throw new IllegalArgumentException("不支援的操作類型: " + itemDto.getAction());
                    }
                    
                    return OrderItemResponseDto.builder()
                            .id(orderItem.getId())
                            .productId(orderItem.getProductId())
                            .productName(orderItem.getProductName())
                            .productType(orderItem.getProductType())
                            .qty(orderItem.getQty())
                            .unitPrice(orderItem.getUnitPrice())
                            .subtotal(orderItem.getSubtotal())
                            .notes(orderItem.getNotes())
                            .createdAt(orderItem.getCreatedAt())
                            .build();
                })
                .filter(item -> item != null) // 過濾掉被刪除的項目
                .toList();
    }
    //endregion
}