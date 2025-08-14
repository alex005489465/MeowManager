package erp.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 產品名稱請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品名稱請求")
public class ProductSearchNameRequest {

    @NotBlank(message = "商品名稱不能為空")
    @Schema(description = "商品名稱", example = "雞肉味餅乾", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}