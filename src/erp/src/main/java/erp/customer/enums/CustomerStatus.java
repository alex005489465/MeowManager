package erp.customer.enums;

import erp.common.enums.CodedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 客戶狀態枚舉
 */
@Getter
@Schema(description = "客戶狀態", example = "ACTIVE")
public enum CustomerStatus implements CodedEnum {
    ACTIVE(1, "啟用"),
    SUSPENDED(2, "暫停"),
    BLACKLIST(3, "黑名單");

    private final int code;
    private final String description;

    CustomerStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

}