package erp.payment.dto;

import erp.payment.enums.PaymentMethod;
import erp.payment.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 付款響應DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "付款響應")
public class PaymentResponse {

    /**
     * 付款記錄ID
     */
    @Schema(description = "付款記錄ID", example = "1")
    private Long id;

    /**
     * 訂單ID
     */
    @Schema(description = "訂單ID", example = "1")
    private Long orderId;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式", example = "CASH")
    private PaymentMethod paymentMethod;

    /**
     * 支付金額
     */
    @Schema(description = "支付金額", example = "500.00")
    private BigDecimal amount;

    /**
     * 支付時間
     */
    @Schema(description = "支付時間", example = "2025-08-06T22:18:00")
    private LocalDateTime paidTime;

    /**
     * 付款狀態
     */
    @Schema(description = "付款狀態", example = "PAID")
    private PaymentStatus status;

    /**
     * 支付備註
     */
    @Schema(description = "支付備註", example = "支付平台交易號: TXN123456789")
    private String notes;

    /**
     * 建立時間
     */
    @Schema(description = "建立時間", example = "2025-08-06T22:18:00")
    private LocalDateTime createdAt;

    /**
     * 更新時間
     */
    @Schema(description = "更新時間", example = "2025-08-06T22:18:00")
    private LocalDateTime updatedAt;
}