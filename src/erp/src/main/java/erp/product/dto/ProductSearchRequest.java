package erp.product.dto;
import erp.common.dto.BasePageableRequest;
import erp.product.enums.ProductType;
import erp.product.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 產品多條件搜索請求DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品多條件搜索請求")
public class ProductSearchRequest extends BasePageableRequest {

    /**
     * 商品名稱（模糊搜索）
     */
    @Schema(description = "商品名稱", example = "餅乾")
    private String name;

    /**
     * 商品類型
     */
    @Schema(description = "商品類型", example = "PET_FOOD")
    private ProductType type;

    /**
     * 商品狀態
     */
    @Schema(description = "商品狀態", example = "ACTIVE")
    private ProductStatus status;

    /**
     * 最低價格
     */
    @Schema(description = "最低價格", example = "100.00")
    private BigDecimal minPrice;

    /**
     * 最高價格
     */
    @Schema(description = "最高價格", example = "1000.00")
    private BigDecimal maxPrice;

    /**
     * 商品描述（模糊搜索）
     */
    @Schema(description = "商品描述", example = "洗澡")
    private String description;
}