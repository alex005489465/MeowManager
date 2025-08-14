package erp.order.service;

import erp.order.dto.OrderCreateRequestDto;
import erp.order.dto.OrderCreateResponseDto;
import erp.order.dto.OrderUpdateRequestDto;
import erp.order.dto.OrderUpdateResponseDto;
import erp.order.dto.OrderStatusUpdateDto;
import erp.order.entity.Order;
import erp.order.enums.OrderStatus;
import erp.payment.enums.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 訂單服務介面
 * 專門處理訂單主表的業務操作
 */
public interface OrderService {
    
    //region 訂單查詢相關方法 (Read)
    Order getOrderById(Long id);
    Order getOrderByNo(String orderNo);
    Page<Order> getAllOrders(Pageable pageable);
    List<Order> getOrdersByCustomerId(Long customerId);
    List<Order> getOrdersByStatus(OrderStatus status);
    List<Order> getOrdersByPaymentMethod(PaymentMethod paymentMethod);
    List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> getOrdersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount);
    //endregion
    
    //region 訂單操作相關方法 (Create & Update)
    Order createOrder(Long customerId, OrderStatus status, BigDecimal totalAmount, 
                     BigDecimal discountAmount, BigDecimal finalAmount, 
                     PaymentMethod paymentMethod, String notes);
    Order updateOrder(Long orderId, Long customerId, OrderStatus status, 
                     BigDecimal totalAmount, BigDecimal discountAmount, 
                     BigDecimal finalAmount, PaymentMethod paymentMethod, String notes);
    Order updateOrderStatus(Long orderId, OrderStatus status);
    //endregion
    
    //region 控制器專用業務邏輯區塊 - 創建相關
    OrderCreateResponseDto createOrder(OrderCreateRequestDto request);
    //endregion
    
    //region 控制器專用業務邏輯區塊 - 更新相關
    OrderUpdateResponseDto updateOrder(OrderUpdateRequestDto request);
    OrderUpdateResponseDto updateOrderStatus(OrderStatusUpdateDto request);
    //endregion
}