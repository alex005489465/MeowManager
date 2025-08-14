package erp.stock.enums;

import erp.common.enums.CodedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 庫存異動類型枚舉
 */
@Getter
@Schema(description = "庫存異動類型", example = "IN")
public enum MovementType implements CodedEnum {
    IN(1, "入庫"),
    OUT(2, "出庫");

    private final int code;
    private final String description;

    MovementType(int code, String description) {
        this.code = code;
        this.description = description;
    }
}