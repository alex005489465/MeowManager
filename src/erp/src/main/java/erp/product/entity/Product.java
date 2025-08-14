package erp.product.entity;

import erp.product.enums.ProductType;
import erp.product.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品表實體類
 * 對應資料庫表：products
 */
@Entity
@Schema(description = "商品實體")
@Table(name = "products", indexes = {
    @Index(name = "idx_product_name", columnList = "name"),
    @Index(name = "idx_product_type", columnList = "type"),
    @Index(name = "idx_product_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    /**
     * 商品ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '商品ID'")
    @Schema(description = "商品ID", example = "1")
    private Long id;

    /**
     * 商品名稱
     */
    @Column(name = "name", length = 200, nullable = false, columnDefinition = "VARCHAR(200) COMMENT '商品名稱'")
    @NotNull(message = "商品名稱不能為空")
    @Size(max = 200, message = "商品名稱長度不能超過200個字符")
    @Schema(description = "商品名稱", example = "雞肉味餅乾", maxLength = 200)
    private String name;

    /**
     * 商品類型：1= 寵物食品、2= 寵物服務、3= 寵物用品
     */
    @Column(name = "type", nullable = false, columnDefinition = "TINYINT COMMENT '商品類型：1= 寵物食品、2= 寵物服務、3= 寵物用品'")
    @NotNull(message = "商品類型不能為空")
    @Schema(description = "商品類型", example = "SERVICE")
    private ProductType type;

    /**
     * 商品價格
     */
    @Column(name = "price", precision = 10, scale = 2, nullable = false, columnDefinition = "DECIMAL(10,2) COMMENT '商品價格'")
    @NotNull(message = "商品價格不能為空")
    @DecimalMin(value = "0.00", message = "商品價格不能為負數")
    @Schema(description = "商品價格", example = "500.00")
    private BigDecimal price;

    /**
     * 商品描述
     */
    @Column(name = "description", columnDefinition = "TEXT COMMENT '商品描述'")
    @Schema(description = "商品描述", example = "專業寵物洗澡服務，包含洗澡、吹乾、基礎美容")
    private String description;

    /**
     * 商品狀態：1=啟用，0=停用
     */
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT COMMENT '商品狀態：1=啟用，0=停用'")
    @NotNull(message = "商品狀態不能為空")
    @Schema(description = "商品狀態", example = "ACTIVE")
    private ProductStatus status;

    /**
     * 商品備註
     */
    @Column(name = "notes", columnDefinition = "TEXT COMMENT '商品備註'")
    @Schema(description = "商品備註", example = "適合所有犬種，建議每月一次")
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
     * 在持久化之前設置創建時間
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = ProductStatus.ACTIVE; // 預設為啟用狀態
        }
    }

    /**
     * 在更新之前設置更新時間
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}