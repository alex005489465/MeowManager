package erp.order.entity;

import erp.product.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 訂單明細表實體類
 * 對應資料庫表：order_items
 */
@Entity
@Schema(description = "訂單明細實體")
@Table(name = "order_items", indexes = {
    @Index(name = "idx_order_id", columnList = "order_id"),
    @Index(name = "idx_product_id", columnList = "product_id"),
    @Index(name = "idx_product_type", columnList = "product_type")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    /**
     * 明細ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '明細ID'")
    @Schema(description = "明細ID", example = "1")
    private Long id;

    /**
     * 對應 orders.id
     */
    @Column(name = "order_id", nullable = false, columnDefinition = "BIGINT COMMENT '對應 orders.id'")
    @NotNull(message = "訂單ID不能為空")
    @Schema(description = "訂單ID", example = "1")
    private Long orderId;

    /**
     * 對應 products.id
     */
    @Column(name = "product_id", columnDefinition = "BIGINT COMMENT '對應 products.id'")
    @Schema(description = "商品ID", example = "1")
    private Long productId;

    /**
     * 商品名稱（冗餘存，保留下單當時的名稱）
     */
    @Column(name = "product_name", length = 200, columnDefinition = "VARCHAR(200) COMMENT '商品名稱（冗餘存，保留下單當時的名稱）'")
    @Size(max = 200, message = "商品名稱長度不能超過200個字符")
    @Schema(description = "商品名稱", example = "雞肉味餅乾", maxLength = 200)
    private String productName;

    /**
     * 商品類型：1= 寵物食品、2= 寵物服務、3= 寵物用品
     */
    @Column(name = "product_type", columnDefinition = "TINYINT COMMENT '商品類型：1= 寵物食品、2= 寵物服務、3= 寵物用品'")
    @Schema(description = "商品類型", example = "SERVICE")
    private ProductType productType;

    /**
     * 購買數量
     */
    @Column(name = "qty", nullable = false, columnDefinition = "INT COMMENT '購買數量'")
    @NotNull(message = "購買數量不能為空")
    @Min(value = 1, message = "購買數量必須大於0")
    @Schema(description = "購買數量", example = "2")
    private Integer qty;

    /**
     * 單價（下單當時）
     */
    @Column(name = "unit_price", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) COMMENT '單價（下單當時）'")
    @DecimalMin(value = "0.00", message = "單價不能為負數")
    @Schema(description = "單價", example = "500.00")
    private BigDecimal unitPrice;

    /**
     * 小計（qty * unit_price）
     */
    @Column(name = "subtotal", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) COMMENT '小計（qty * unit_price）'")
    @DecimalMin(value = "0.00", message = "小計不能為負數")
    @Schema(description = "小計", example = "1000.00")
    private BigDecimal subtotal;

    /**
     * 備註（例如毛色、生日、服務細節）
     */
    @Column(name = "notes", columnDefinition = "TEXT COMMENT '備註（例如毛色、生日、服務細節）'")
    @Schema(description = "備註資訊", example = "毛色：白色，生日：2020-01-01，服務細節：需要特別小心")
    private String notes;

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
     * 在持久化之前設置創建時間和計算小計
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        calculateSubtotal();
    }

    /**
     * 在更新之前設置更新時間和計算小計
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateSubtotal();
    }

    /**
     * 計算小計
     */
    private void calculateSubtotal() {
        if (qty != null && unitPrice != null) {
            subtotal = unitPrice.multiply(BigDecimal.valueOf(qty));
        }
    }
}