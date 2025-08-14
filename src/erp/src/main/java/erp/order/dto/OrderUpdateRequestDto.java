package erp.order.dto;

import erp.order.enums.OrderStatus;
import erp.payment.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Schema(description = "更新訂單請求DTO")
public class OrderUpdateRequestDto {
    
    @Schema(description = "訂單ID", example = "1")
    @NotNull(message = "訂單ID不能為空")
    private Long id;
    
    @Schema(description = "訂單編號", example = "ORD20250810001")
    @Size(max = 50, message = "訂單編號長度不能超過50個字符")
    private String no;
    
    @Schema(description = "客戶ID", example = "1")
    private Long customerId;
    
    @Schema(description = "下單時間", example = "2025-08-10T17:29:00")
    private LocalDateTime orderTime;
    
    @Schema(description = "訂單狀態", example = "CONFIRMED")
    private OrderStatus status;
    
    @Schema(description = "折扣金額", example = "100.00")
    @DecimalMin(value = "0.00", message = "折扣金額不能為負數")
    private BigDecimal discountAmount;
    
    @Schema(description = "支付方式", example = "CASH")
    private PaymentMethod paymentMethod;
    
    @Schema(description = "備註資訊", example = "寵物名：小白，特殊需求：需要溫柔對待")
    private String notes;
    
    @Schema(description = "訂單物品清單")
    @Valid
    private List<OrderItemUpdateDto> orderItems;
}