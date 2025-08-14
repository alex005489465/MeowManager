package erp.product.enums;

import erp.common.enums.CodedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 商品類型枚舉
 */
@Getter
@Schema(description = "商品類型", example = "寵物食品")
public enum ProductType implements CodedEnum {
    PET_FOOD(1, "寵物食品"),
    PET_SERVICE(2, "寵物服務"),
    PET_SUPPLIES(3, "寵物用品");

    private final int code;
    private final String description;

    ProductType(int code, String description) {
        this.code = code;
        this.description = description;
    }

}