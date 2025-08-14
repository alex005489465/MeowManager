package erp.order.dto;

import erp.product.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "更新訂單物品DTO")
public class OrderItemUpdateDto {
    
    @Schema(description = "明細ID (新增項目時為null)", example = "1")
    private Long id;
    
    @Schema(description = "商品ID", example = "1")
    @NotNull(message = "商品ID不能為空")
    private Long productId;
    
    @Schema(description = "商品名稱", example = "雞肉味餅乾")
    @Size(max = 200, message = "商品名稱長度不能超過200個字符")
    private String productName;
    
    @Schema(description = "商品類型", example = "FOOD")
    private ProductType productType;
    
    @Schema(description = "購買數量", example = "2")
    @NotNull(message = "購買數量不能為空")
    @Min(value = 1, message = "購買數量必須大於0")
    private Integer qty;
    
    @Schema(description = "單價", example = "500.00")
    @NotNull(message = "單價不能為空")
    @DecimalMin(value = "0.00", message = "單價不能為負數")
    private BigDecimal unitPrice;
    
    @Schema(description = "備註資訊", example = "毛色：白色，特別小心處理")
    private String notes;
    
    @Schema(description = "操作類型：ADD新增, UPDATE更新, DELETE刪除", example = "UPDATE")
    private String action; // ADD, UPDATE, DELETE
}