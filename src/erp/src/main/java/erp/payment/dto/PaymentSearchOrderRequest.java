package erp.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 按訂單查詢付款請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "按訂單查詢付款請求")
public class PaymentSearchOrderRequest {

    /**
     * 訂單ID
     */
    @NotNull(message = "訂單ID不能為空")
    @Schema(description = "訂單ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;
}