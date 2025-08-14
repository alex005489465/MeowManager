package erp.product.dto;

import erp.product.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 產品類型請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品類型請求")
public class ProductSearchTypeRequest {

    @NotNull(message = "商品類型不能為空")
    @Schema(description = "商品類型", example = "PET_FOOD", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProductType type;
}