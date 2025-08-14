package erp.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 庫存響應DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "庫存響應")
public class StockResponse {

    @Schema(description = "庫存ID", example = "1")
    private Long id;

    @Schema(description = "商品ID", example = "1")
    private Long productId;

    @Schema(description = "商品名稱", example = "皇家貓糧")
    private String productName; // 冗餘欄位，方便前端顯示

    @Schema(description = "庫存數量", example = "100")
    private Integer qty;

    @Schema(description = "平均成本", example = "25.50")
    private BigDecimal avgCost;

    @Schema(description = "庫存價值", example = "2550.00")
    private BigDecimal totalCost;

    @Schema(description = "最後更新時間", example = "2025-08-10T01:19:00")
    private LocalDateTime updatedAt;
}