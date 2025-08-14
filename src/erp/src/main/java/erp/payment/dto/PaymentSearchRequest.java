package erp.payment.dto;

import erp.common.dto.BasePageableRequest;
import erp.payment.enums.PaymentMethod;
import erp.payment.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 付款查詢請求DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "付款查詢請求")
public class PaymentSearchRequest extends BasePageableRequest {

    /**
     * 訂單ID（可選）
     */
    @Schema(description = "訂單ID", example = "1")
    private Long orderId;

    /**
     * 支付方式（可選）
     */
    @Schema(description = "支付方式", example = "CASH")
    private PaymentMethod paymentMethod;

    /**
     * 付款狀態（可選）
     */
    @Schema(description = "付款狀態", example = "PAID")
    private PaymentStatus status;

    /**
     * 開始時間（可選）
     */
    @Schema(description = "開始時間", example = "2025-08-01T00:00:00")
    private LocalDateTime startTime;

    /**
     * 結束時間（可選）
     */
    @Schema(description = "結束時間", example = "2025-08-31T23:59:59")
    private LocalDateTime endTime;
}