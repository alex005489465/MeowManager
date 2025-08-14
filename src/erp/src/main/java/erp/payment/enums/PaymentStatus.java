package erp.payment.enums;

import erp.common.enums.CodedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 支付狀態枚舉
 */
@Getter
@Schema(description = "支付狀態", example = "PAID")
public enum PaymentStatus implements CodedEnum {
    UNPAID(0, "未支付"),
    PAID(1, "已支付"),
    CANCELLED(2, "取消");

    private final int code;
    private final String description;

    PaymentStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

}