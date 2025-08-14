package erp.stock.dto;

import erp.stock.enums.MovementType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 庫存異動創建請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "庫存異動創建請求")
public class StockMovementCreateRequest {

    @NotNull(message = "庫存ID不能為空")
    @Schema(description = "庫存ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long stockId;

    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;

    @NotNull(message = "異動類型不能為空")
    @Schema(description = "異動類型", example = "IN", requiredMode = Schema.RequiredMode.REQUIRED)
    private MovementType movementType;

    @NotNull(message = "異動數量不能為空")
    @Min(value = 1, message = "異動數量必須大於0")
    @Schema(description = "異動數量", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer qty;

    @DecimalMin(value = "0.00", message = "單位成本不能為負數")
    @Schema(description = "單位成本", example = "25.00")
    private BigDecimal unitCost;
}