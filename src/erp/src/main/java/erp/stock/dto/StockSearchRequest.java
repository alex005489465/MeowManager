package erp.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 庫存查詢請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "庫存查詢請求")
public class StockSearchRequest {

    @Schema(description = "商品ID", example = "1")
    private Long productId;

    @Schema(description = "最小庫存數量", example = "10")
    private Integer minQty;

    @Schema(description = "最大庫存數量", example = "1000")
    private Integer maxQty;

    @Schema(description = "最小平均成本", example = "10.00")
    private BigDecimal minAvgCost;

    @Schema(description = "最大平均成本", example = "100.00")
    private BigDecimal maxAvgCost;
}