package erp.product.enums;

import erp.common.enums.CodedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 商品狀態枚舉
 */
@Getter
@Schema(description = "商品狀態", example = "ACTIVE")
public enum ProductStatus implements CodedEnum {
    DISABLED(0, "停用"),
    ACTIVE(1, "啟用");

    private final int code;
    private final String description;

    ProductStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

}