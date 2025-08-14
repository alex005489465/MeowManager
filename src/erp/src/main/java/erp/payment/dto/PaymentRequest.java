package erp.payment.dto;

import erp.payment.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 付款請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "付款請求")
public class PaymentRequest {

    /**
     * 訂單ID
     */
    @NotNull(message = "訂單ID不能為空")
    @Schema(description = "訂單ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    /**
     * 支付方式
     */
    @NotNull(message = "支付方式不能為空")
    @Schema(description = "支付方式", example = "CASH", requiredMode = Schema.RequiredMode.REQUIRED)
    private PaymentMethod paymentMethod;

    /**
     * 支付金額
     */
    @NotNull(message = "支付金額不能為空")
    @DecimalMin(value = "0.01", message = "支付金額必須大於0")
    @Schema(description = "支付金額", example = "500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    /**
     * 支付備註（可選）
     */
    @Schema(description = "支付備註", example = "支付平台交易號: TXN123456789")
    private String notes;
}