package erp.payment.dto;

import erp.payment.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 根據支付方式搜索訂單請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "根據支付方式搜索訂單請求")
public class OrderSearchPaymentMethodRequest {

    /**
     * 支付方式
     */
    @NotNull(message = "支付方式不能為空")
    @Schema(description = "支付方式", example = "CASH", requiredMode = Schema.RequiredMode.REQUIRED)
    private PaymentMethod paymentMethod;
}