package erp.stock.dto;

import erp.stock.enums.MovementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 庫存異動響應DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "庫存異動響應")
public class StockMovementResponse {

    @Schema(description = "異動記錄ID", example = "1")
    private Long id;

    @Schema(description = "庫存ID", example = "1")
    private Long stockId;

    @Schema(description = "商品ID", example = "1")
    private Long productId;

    @Schema(description = "商品名稱", example = "皇家貓糧")
    private String productName; // 冗餘欄位

    @Schema(description = "異動類型", example = "IN")
    private MovementType movementType;

    @Schema(description = "異動數量", example = "10")
    private Integer qty;

    @Schema(description = "單位成本", example = "25.00")
    private BigDecimal unitCost;

    @Schema(description = "異動總成本", example = "250.00")
    private BigDecimal totalCost;

    @Schema(description = "建立時間", example = "2025-08-10T01:19:00")
    private LocalDateTime createdAt;
    
}