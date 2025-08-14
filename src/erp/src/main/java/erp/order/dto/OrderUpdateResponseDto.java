package erp.order.dto;

import erp.order.enums.OrderStatus;
import erp.payment.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "更新訂單回應DTO")
public class OrderUpdateResponseDto {
    
    @Schema(description = "訂單ID", example = "1")
    private Long id;
    
    @Schema(description = "訂單編號", example = "ORD20250810001")
    private String no;
    
    @Schema(description = "客戶ID", example = "1")
    private Long customerId;
    
    @Schema(description = "下單時間", example = "2025-08-10T17:29:00")
    private LocalDateTime orderTime;
    
    @Schema(description = "訂單狀態", example = "CONFIRMED")
    private OrderStatus status;
    
    @Schema(description = "訂單總額", example = "1000.00")
    private BigDecimal totalAmount;
    
    @Schema(description = "折扣金額", example = "100.00")
    private BigDecimal discountAmount;
    
    @Schema(description = "實付金額", example = "900.00")
    private BigDecimal finalAmount;
    
    @Schema(description = "支付方式", example = "CASH")
    private PaymentMethod paymentMethod;
    
    @Schema(description = "備註資訊")
    private String notes;
    
    @Schema(description = "更新時間", example = "2025-08-10T18:40:00")
    private LocalDateTime updatedAt;
    
    @Schema(description = "訂單物品清單")
    private List<OrderItemResponseDto> orderItems;
}