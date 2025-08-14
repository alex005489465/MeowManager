package erp.stock.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 庫存快照表實體類
 * 對應資料庫表：stock
 */
@Entity
@Schema(description = "庫存快照實體")
@Table(name = "stock", indexes = {
    @Index(name = "idx_stock_product", columnList = "product_id"),
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    /**
     * 庫存ID（主鍵）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '庫存ID'")
    @Schema(description = "庫存ID", example = "1")
    private Long id;

    /**
     * 商品ID
     */
    @Column(name = "product_id", nullable = false, columnDefinition = "BIGINT COMMENT '商品ID'")
    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID", example = "1")
    private Long productId;

    /**
     * 庫存數量
     */
    @Column(name = "qty", nullable = false, columnDefinition = "INT COMMENT '庫存數量'")
    @NotNull(message = "庫存數量不能為空")
    @Min(value = 0, message = "庫存數量不能為負數")
    @Schema(description = "庫存數量", example = "100")
    private Integer qty = 0;

    /**
     * 平均成本
     */
    @Column(name = "avg_cost", precision = 10, scale = 2, nullable = false, 
            columnDefinition = "DECIMAL(10,2) COMMENT '平均成本'")
    @NotNull(message = "平均成本不能為空")
    @DecimalMin(value = "0.00", message = "平均成本不能為負數")
    @Schema(description = "平均成本", example = "25.50")
    private BigDecimal avgCost = BigDecimal.ZERO;

    /**
     * 庫存價值
     */
    @Column(name = "total_cost", precision = 10, scale = 2, nullable = false, 
            columnDefinition = "DECIMAL(10,2) COMMENT '庫存價值'")
    @NotNull(message = "庫存價值不能為空")
    @DecimalMin(value = "0.00", message = "庫存價值不能為負數")
    @Schema(description = "庫存價值", example = "2550.00")
    private BigDecimal totalCost = BigDecimal.ZERO;

    /**
     * 建立時間
     */
    @Column(name = "created_at", columnDefinition = "DATETIME(3) COMMENT '建立時間'")
    @Schema(description = "建立時間", example = "2025-08-06T22:18:00")
    private LocalDateTime createdAt;

    /**
     * 最後更新時間
     */
    @Column(name = "updated_at", columnDefinition = "DATETIME(3) COMMENT '最後更新時間'")
    @Schema(description = "最後更新時間", example = "2025-08-06T22:18:00")
    private LocalDateTime updatedAt;

    /**
     * 在持久化之前設置創建時間
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    /**
     * 在更新之前設置更新時間
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}