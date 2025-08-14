package erp.stock.dto;

import erp.stock.enums.MovementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 庫存異動查詢請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "庫存異動查詢請求")
public class StockMovementSearchRequest {

    @Schema(description = "庫存ID", example = "1")
    private Long stockId;

    @Schema(description = "商品ID", example = "1")
    private Long productId;

    @Schema(description = "異動類型", example = "IN")
    private MovementType movementType;

    @Schema(description = "開始時間", example = "2025-08-01T00:00:00")
    private LocalDateTime startTime;

    @Schema(description = "結束時間", example = "2025-08-31T23:59:59")
    private LocalDateTime endTime;

    @Schema(description = "最小異動數量", example = "1")
    private Integer minQty;

    @Schema(description = "最大異動數量", example = "100")
    private Integer maxQty;
}