package erp.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 產品價格範圍請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品價格範圍請求")
public class ProductSearchPriceRangeRequest {

    /**
     * 最低價格
     */
    @DecimalMin(value = "0.00", message = "最低價格不能為負數")
    @Schema(description = "最低價格", example = "100.00")
    private BigDecimal minPrice;

    /**
     * 最高價格
     */
    @DecimalMin(value = "0.00", message = "最高價格不能為負數")
    @Schema(description = "最高價格", example = "1000.00")
    private BigDecimal maxPrice;
}