package erp.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 庫存入庫請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "庫存入庫請求")
public class StockInboundRequest {

    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;

    @NotNull(message = "入庫數量不能為空")
    @Min(value = 1, message = "入庫數量必須大於0")
    @Schema(description = "入庫數量", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer qty;

    @DecimalMin(value = "0.00", message = "單位成本不能為負數")
    @Schema(description = "單位成本", example = "25.00")
    private BigDecimal unitCost;

    @Schema(description = "入庫原因", example = "採購入庫")
    private String reason;
}