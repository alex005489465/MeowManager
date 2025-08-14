package erp.payment.enums;

import erp.common.enums.CodedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 支付方式枚舉
 */
@Getter
@Schema(description = "支付方式", example = "CASH")
public enum PaymentMethod implements CodedEnum {
    CASH(1, "現金"),
    CREDIT_CARD(2, "信用卡"),
    LINE_PAY(3, "LINE Pay"),
    BANK_TRANSFER(4, "銀行轉帳"),
    MOBILE_PAYMENT(5, "行動支付"),
    OTHER(99, "其他");

    private final int code;
    private final String description;

    PaymentMethod(int code, String description) {
        this.code = code;
        this.description = description;
    }

}