package erp.order.service.impl;

import erp.order.constant.OrderConstants;
import erp.order.entity.Order;
import erp.order.enums.OrderStatus;
import erp.order.repository.OrderRepository;
import erp.payment.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 訂單創建更新功能實現
 * 專門處理訂單相關的創建和更新業務邏輯
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderCreateUpdateServiceImpl {

    private final OrderRepository orderRepository;
    private final OrderQueryServiceImpl queryService;

    /**
     * 創建訂單
     * @param customerId 客戶ID
     * @param status 訂單狀態
     * @param totalAmount 總金額
     * @param discountAmount 折扣金額
     * @param finalAmount 最終金額
     * @param paymentMethod 付款方式
     * @param notes 備註
     * @return 創建的訂單
     */
    public Order createOrder(Long customerId, OrderStatus status, BigDecimal totalAmount, 
                            BigDecimal discountAmount, BigDecimal finalAmount, 
                            PaymentMethod paymentMethod, String notes) {
        log.info(OrderConstants.LOG_CREATE_ORDER, customerId);
        
        // 生成訂單編號
        String orderNo = generateOrderNo();
        
        // 構建訂單實體
        Order order = Order.builder()
                .no(orderNo)
                .customerId(customerId)
                .status(status != null ? status : OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount != null ? discountAmount : BigDecimal.ZERO)
                .finalAmount(finalAmount)
                .paymentMethod(paymentMethod)
                .notes(notes)
                .orderTime(LocalDateTime.now())
                .build();
        
        // 驗證訂單數據
        validateOrder(order);
        
        // 保存訂單
        Order savedOrder = orderRepository.save(order);
        log.info("訂單創建成功，ID: {}, 編號: {}", savedOrder.getId(), savedOrder.getNo());
        return savedOrder;
    }

    /**
     * 創建訂單（使用訂單實體）
     * @param order 訂單實體
     * @return 創建的訂單
     */
    public Order createOrder(Order order) {
        log.info(OrderConstants.LOG_CREATE_ORDER, order.getCustomerId());
        
        // 如果沒有訂單編號，生成一個
        if (order.getNo() == null || order.getNo().isEmpty()) {
            order.setNo(generateOrderNo());
        }
        
        // 設置預設值
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING);
        }
        
        if (order.getDiscountAmount() == null) {
            order.setDiscountAmount(BigDecimal.ZERO);
        }
        
        if (order.getOrderTime() == null) {
            order.setOrderTime(LocalDateTime.now());
        }
        
        // 驗證訂單數據
        validateOrder(order);
        
        // 保存訂單
        Order savedOrder = orderRepository.save(order);
        log.info("訂單創建成功，ID: {}, 編號: {}", savedOrder.getId(), savedOrder.getNo());
        return savedOrder;
    }

    /**
     * 更新訂單
     * @param orderId 訂單ID
     * @param customerId 客戶ID
     * @param status 訂單狀態
     * @param totalAmount 總金額
     * @param discountAmount 折扣金額
     * @param finalAmount 最終金額
     * @param paymentMethod 付款方式
     * @param notes 備註
     * @return 更新後的訂單
     */
    public Order updateOrder(Long orderId, Long customerId, OrderStatus status, 
                            BigDecimal totalAmount, BigDecimal discountAmount, 
                            BigDecimal finalAmount, PaymentMethod paymentMethod, String notes) {
        log.info(OrderConstants.LOG_UPDATE_ORDER, orderId);
        
        // 獲取現有訂單
        Order existingOrder = queryService.getOrderById(orderId);
        
        // 檢查訂單狀態是否允許修改
        validateOrderModifiable(existingOrder);
        
        // 更新訂單欄位
        updateOrderFields(existingOrder, customerId, status, totalAmount, discountAmount, 
                         finalAmount, paymentMethod, notes);
        
        // 驗證更新後的訂單數據
        validateOrder(existingOrder);
        
        // 保存更新
        Order updatedOrder = orderRepository.save(existingOrder);
        log.info("訂單更新成功，ID: {}", updatedOrder.getId());
        return updatedOrder;
    }

    /**
     * 更新訂單（使用訂單實體）
     * @param orderId 訂單ID
     * @param order 新的訂單數據
     * @return 更新後的訂單
     */
    public Order updateOrder(Long orderId, Order order) {
        log.info(OrderConstants.LOG_UPDATE_ORDER, orderId);
        
        // 獲取現有訂單
        Order existingOrder = queryService.getOrderById(orderId);
        
        // 檢查訂單狀態是否允許修改
        validateOrderModifiable(existingOrder);
        
        // 更新訂單欄位
        updateOrderFields(existingOrder, order);
        
        // 驗證更新後的訂單數據
        validateOrder(existingOrder);
        
        // 保存更新
        Order updatedOrder = orderRepository.save(existingOrder);
        log.info("訂單更新成功，ID: {}", updatedOrder.getId());
        return updatedOrder;
    }

    /**
     * 更新訂單狀態
     * @param orderId 訂單ID
     * @param status 新狀態
     * @return 更新後的訂單
     */
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        log.info(OrderConstants.LOG_UPDATE_ORDER_STATUS, orderId, status);
        
        // 獲取現有訂單
        Order order = queryService.getOrderById(orderId);
        
        // 驗證狀態變更是否合法
        validateStatusChange(order.getStatus(), status);
        
        // 更新狀態
        order.setStatus(status);
        
        // 保存更新
        Order updatedOrder = orderRepository.save(order);
        log.info("訂單狀態更新成功，ID: {}, 新狀態: {}", updatedOrder.getId(), status);
        return updatedOrder;
    }

    /**
     * 生成訂單編號
     * @return 訂單編號
     */
    private String generateOrderNo() {
        // 格式: ORD + 年月日 + 6位序號
        String datePart = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = OrderConstants.ORDER_NO_PREFIX + datePart;
        
        // 查找當天最大的序號
        String maxOrderNo = orderRepository.findByNoContainingIgnoreCase(prefix)
                .stream()
                .filter(order -> order.getNo().startsWith(prefix))
                .map(Order::getNo)
                .max(String::compareTo)
                .orElse(prefix + "000000");
        
        // 生成下一個序號
        int nextSequence = 1;
        if (maxOrderNo.length() >= prefix.length() + 6) {
            try {
                String sequencePart = maxOrderNo.substring(prefix.length());
                nextSequence = Integer.parseInt(sequencePart) + 1;
            } catch (NumberFormatException e) {
                log.warn("無法解析序號，使用預設值: {}", maxOrderNo);
            }
        }
        
        return String.format("%s%06d", prefix, nextSequence);
    }

    /**
     * 驗證訂單數據
     * @param order 訂單實體
     */
    private void validateOrder(Order order) {
        // 驗證客戶ID
        if (order.getCustomerId() == null) {
            throw new IllegalArgumentException("客戶ID不能為空");
        }
        
        // 驗證金額
        if (order.getTotalAmount() == null || order.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("總金額不能為空且不能為負數");
        }
        
        if (order.getFinalAmount() == null || order.getFinalAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("最終金額不能為空且不能為負數");
        }
        
        if (order.getDiscountAmount() != null && order.getDiscountAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("折扣金額不能為負數");
        }
        
        // 驗證訂單編號唯一性
        if (order.getId() == null && queryService.existsByOrderNo(order.getNo())) {
            throw new IllegalArgumentException("訂單編號已存在: " + order.getNo());
        } else if (order.getId() != null && queryService.existsByOrderNoExcludingId(order.getNo(), order.getId())) {
            throw new IllegalArgumentException("訂單編號已被其他訂單使用: " + order.getNo());
        }
    }

    /**
     * 驗證訂單是否可修改
     * @param order 訂單實體
     */
    private void validateOrderModifiable(Order order) {
        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException(OrderConstants.ORDER_STATUS_NOT_MODIFIABLE + ": " + order.getStatus());
        }
    }

    /**
     * 驗證狀態變更是否合法
     * @param currentStatus 當前狀態
     * @param newStatus 新狀態
     */
    private void validateStatusChange(OrderStatus currentStatus, OrderStatus newStatus) {
        // 已完成或已取消的訂單不能再變更狀態
        if (currentStatus == OrderStatus.COMPLETED || currentStatus == OrderStatus.CANCELLED) {
            if (newStatus != currentStatus) {
                throw new IllegalStateException(OrderConstants.ORDER_STATUS_NOT_MODIFIABLE + ": " + currentStatus);
            }
        }
        
        // 其他業務規則驗證可以在這裡添加
    }

    /**
     * 更新訂單欄位
     * @param existingOrder 現有訂單
     * @param customerId 客戶ID
     * @param status 狀態
     * @param totalAmount 總金額
     * @param discountAmount 折扣金額
     * @param finalAmount 最終金額
     * @param paymentMethod 付款方式
     * @param notes 備註
     */
    private void updateOrderFields(Order existingOrder, Long customerId, OrderStatus status, 
                                  BigDecimal totalAmount, BigDecimal discountAmount, 
                                  BigDecimal finalAmount, PaymentMethod paymentMethod, String notes) {
        if (customerId != null) {
            existingOrder.setCustomerId(customerId);
        }
        if (status != null) {
            existingOrder.setStatus(status);
        }
        if (totalAmount != null) {
            existingOrder.setTotalAmount(totalAmount);
        }
        if (discountAmount != null) {
            existingOrder.setDiscountAmount(discountAmount);
        }
        if (finalAmount != null) {
            existingOrder.setFinalAmount(finalAmount);
        }
        if (paymentMethod != null) {
            existingOrder.setPaymentMethod(paymentMethod);
        }
        if (notes != null) {
            existingOrder.setNotes(notes);
        }
    }

    /**
     * 更新訂單欄位（使用訂單實體）
     * @param existingOrder 現有訂單
     * @param newOrder 新訂單數據
     */
    private void updateOrderFields(Order existingOrder, Order newOrder) {
        updateOrderFields(existingOrder, 
                         newOrder.getCustomerId(),
                         newOrder.getStatus(),
                         newOrder.getTotalAmount(),
                         newOrder.getDiscountAmount(),
                         newOrder.getFinalAmount(),
                         newOrder.getPaymentMethod(),
                         newOrder.getNotes());
    }
}