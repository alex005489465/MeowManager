package erp.product.dto;

import erp.product.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 產品狀態請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品狀態請求")
public class ProductSearchStatusRequest {

    @NotNull(message = "商品狀態不能為空")
    @Schema(description = "商品狀態", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProductStatus status;
}