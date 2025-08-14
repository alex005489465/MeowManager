package erp.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * 產品統計信息DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "產品統計信息")
public class ProductStatistics {
    
    @Schema(description = "總產品數量", example = "100")
    private Long totalProducts;
    
    @Schema(description = "各狀態產品數量統計")
    private Map<String, Long> statusStatistics;
    
    @Schema(description = "各類型產品數量統計")
    private Map<String, Long> typeStatistics;
    
    @Schema(description = "啟用產品數量", example = "80")
    private Long activeProducts;
    
    @Schema(description = "停用產品數量", example = "20")
    private Long inactiveProducts;
}