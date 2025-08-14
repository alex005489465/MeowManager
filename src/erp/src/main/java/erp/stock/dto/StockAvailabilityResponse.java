package erp.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 庫存可用性響應DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "庫存可用性響應")
public class StockAvailabilityResponse {

    @Schema(description = "商品ID", example = "1")
    private Long productId;

    @Schema(description = "當前庫存數量", example = "50")
    private Integer currentQty;

    @Schema(description = "所需數量", example = "10")
    private Integer requiredQty;

    @Schema(description = "是否有足夠庫存", example = "true")
    private Boolean isAvailable;

    @Schema(description = "缺貨數量（如果庫存不足）", example = "0")
    private Integer shortageQty;
}