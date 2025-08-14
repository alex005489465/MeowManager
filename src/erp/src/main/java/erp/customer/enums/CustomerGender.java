package erp.customer.enums;

import erp.common.enums.CodedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 客戶性別枚舉
 */
@Getter
@Schema(description = "客戶性別", example = "MALE")
public enum CustomerGender implements CodedEnum {
    MALE(1, "男"),
    FEMALE(2, "女"),
    OTHER(3, "其他");

    private final int code;
    private final String description;

    CustomerGender(int code, String description) {
        this.code = code;
        this.description = description;
    }

}