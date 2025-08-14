package erp.order.dto;

import erp.product.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "訂單物品回應DTO")
public class OrderItemResponseDto {
    
    @Schema(description = "明細ID", example = "1")
    private Long id;
    
    @Schema(description = "商品ID", example = "1")
    private Long productId;
    
    @Schema(description = "商品名稱", example = "雞肉味餅乾")
    private String productName;
    
    @Schema(description = "商品類型", example = "FOOD")
    private ProductType productType;
    
    @Schema(description = "購買數量", example = "2")
    private Integer qty;
    
    @Schema(description = "單價", example = "500.00")
    private BigDecimal unitPrice;
    
    @Schema(description = "小計", example = "1000.00")
    private BigDecimal subtotal;
    
    @Schema(description = "備註資訊")
    private String notes;
    
    @Schema(description = "創建時間", example = "2025-08-10T17:29:00")
    private LocalDateTime createdAt;
}