package erp.stock.entity;

import erp.stock.enums.MovementType;
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
 * 庫存異動表實體類
 * 對應資料庫表：stock_movements
 */
@Entity
@Schema(description = "庫存異動實體")
@Table(name = "stock_movements", indexes = {
    @Index(name = "idx_movement_stock", columnList = "stock_id"),
    @Index(name = "idx_movement_product", columnList = "product_id"),
    @Index(name = "idx_movement_type", columnList = "movement_type"),
    @Index(name = "idx_movement_created", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement {

    /**
     * 異動紀錄ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '異動紀錄ID'")
    @Schema(description = "異動紀錄ID", example = "1")
    private Long id;

    /**
     * 對應 stock.id
     */
    @Column(name = "stock_id", nullable = false, columnDefinition = "BIGINT COMMENT '對應 stock.id'")
    @NotNull(message = "庫存ID不能為空")
    @Schema(description = "庫存ID", example = "1")
    private Long stockId;

    /**
     * 商品ID（冗餘欄位，方便查詢）
     */
    @Column(name = "product_id", nullable = false, columnDefinition = "BIGINT COMMENT '商品ID'")
    @NotNull(message = "商品ID不能為空")
    @Schema(description = "商品ID", example = "1")
    private Long productId;

    /**
     * 異動類型：1=入庫、2=出庫
     */
    @Column(name = "movement_type", nullable = false, 
            columnDefinition = "TINYINT COMMENT '異動類型：1=入庫、2=出庫'")
    @NotNull(message = "異動類型不能為空")
    @Schema(description = "異動類型", example = "IN")
    private MovementType movementType;

    /**
     * 異動數量（永遠正數）
     */
    @Column(name = "qty", nullable = false, columnDefinition = "INT COMMENT '異動數量（永遠正數）'")
    @NotNull(message = "異動數量不能為空")
    @Min(value = 1, message = "異動數量必須大於0")
    @Schema(description = "異動數量", example = "10")
    private Integer qty;

    /**
     * 單位成本
     */
    @Column(name = "unit_cost", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) COMMENT '單位成本'")
    @DecimalMin(value = "0.00", message = "單位成本不能為負數")
    @Schema(description = "單位成本", example = "25.00")
    private BigDecimal unitCost;

    /**
     * 異動總成本
     */
    @Column(name = "total_cost", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) COMMENT '異動總成本'")
    @DecimalMin(value = "0.00", message = "異動總成本不能為負數")
    @Schema(description = "異動總成本", example = "250.00")
    private BigDecimal totalCost;

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
     * 在持久化之前設置創建時間並計算總成本
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        calculateTotalCost();
    }

    /**
     * 在更新之前設置更新時間並計算總成本
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateTotalCost();
    }

    /**
     * 計算總成本
     */
    private void calculateTotalCost() {
        if (qty != null && unitCost != null) {
            totalCost = unitCost.multiply(BigDecimal.valueOf(qty));
        }
    }
}