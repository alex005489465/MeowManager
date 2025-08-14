package erp.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 付款取消請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "付款取消請求")
public class PaymentCancelRequest {

    /**
     * 訂單ID
     */
    @NotNull(message = "訂單ID不能為空")
    @Schema(description = "訂單ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    /**
     * 取消原因（可選）
     */
    @Schema(description = "取消原因", example = "客戶要求取消付款")
    private String reason;
}