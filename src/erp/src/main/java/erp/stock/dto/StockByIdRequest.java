package erp.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 根據ID查詢庫存請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "根據產品ID查詢庫存請求")
public class StockByIdRequest {

    @NotNull(message = "產品ID不能為空")
    @Schema(description = "庫存ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;
}