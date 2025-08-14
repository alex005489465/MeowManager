package erp.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 庫存可用性檢查請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "庫存可用性檢查請求")
public class StockAvailabilityRequest {

    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;

    @NotNull(message = "所需數量不能為空")
    @Min(value = 1, message = "所需數量必須大於0")
    @Schema(description = "所需數量", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer requiredQty;
}