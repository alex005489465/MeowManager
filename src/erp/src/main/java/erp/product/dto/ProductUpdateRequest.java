package erp.product.dto;

import erp.product.enums.ProductType;
import erp.product.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 產品更新請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品更新請求")
public class ProductUpdateRequest {

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 商品名稱
     */
    @NotBlank(message = "商品名稱不能為空")
    @Size(max = 200, message = "商品名稱長度不能超過200個字符")
    @Schema(description = "商品名稱", example = "雞肉味餅乾", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 200)
    private String name;

    /**
     * 商品類型：1= 寵物食品、2= 寵物服務、3= 寵物用品
     */
    @NotNull(message = "商品類型不能為空")
    @Schema(description = "商品類型", example = "PET_FOOD", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProductType type;

    /**
     * 商品價格
     */
    @NotNull(message = "商品價格不能為空")
    @DecimalMin(value = "0.00", message = "商品價格不能為負數")
    @Schema(description = "商品價格", example = "500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    /**
     * 商品描述
     */
    @Schema(description = "商品描述", example = "專業寵物洗澡服務，包含洗澡、吹乾、基礎美容")
    private String description;

    /**
     * 商品狀態：1=啟用，0=停用
     */
    //@NotNull(message = "商品狀態不能為空")
    @Schema(description = "商品狀態", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProductStatus status;

    /**
     * 商品備註
     */
    @Schema(description = "商品備註", example = "適合所有犬種，建議每月一次")
    private String notes;
}