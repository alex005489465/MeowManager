package erp.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 產品ID請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品ID請求")
public class ProductSearchIdRequest {
    
    @NotNull(message = "產品ID不能為空")
    @Schema(description = "產品ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}