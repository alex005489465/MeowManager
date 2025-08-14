package erp.product.dto;

import erp.product.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 產品狀態更新請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品狀態更新請求")
public class ProductStatusUpdateRequest {

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 商品狀態
     */
    @NotNull(message = "商品狀態不能為空")
    @Schema(description = "商品狀態", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProductStatus status;
}