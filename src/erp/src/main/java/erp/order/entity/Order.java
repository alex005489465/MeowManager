package erp.order.entity;

import erp.order.enums.OrderStatus;
import erp.payment.enums.PaymentMethod;
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
 * 訂單主表實體類
 * 對應資料庫表：orders
 */
@Entity
@Schema(description = "訂單實體")
@Table(name = "orders", indexes = {
    @Index(name = "idx_order_no", columnList = "order_no"),
    @Index(name = "idx_customer_id", columnList = "customer_id"),
    @Index(name = "idx_order_time", columnList = "order_time"),
    @Index(name = "idx_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    /**
     * 訂單ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '訂單ID'")
    @Schema(description = "訂單ID", example = "1")
    private Long id;

    /**
     * 訂單編號
     */
    @Column(name = "no", length = 50, columnDefinition = "VARCHAR(50) COMMENT '訂單編號（前台顯示用，可帶日期+流水號）'")
    @Size(max = 50, message = "訂單編號長度不能超過50個字符")
    @Schema(description = "訂單編號", example = "ORD20250806001", maxLength = 50)
    private String no;

    /**
     * 客戶ID
     */
    @Column(name = "customer_id", columnDefinition = "BIGINT COMMENT '客戶ID（對應 customers 表，可為 NULL 表示散客）'")
    @Schema(description = "客戶ID", example = "1")
    private Long customerId;

    /**
     * 下單時間
     */
    @Column(name = "order_time", columnDefinition = "DATETIME(3) COMMENT '下單時間'")
    @NotNull(message = "下單時間不能為空")
    @Schema(description = "下單時間", example = "2025-08-06T22:18:00")
    private LocalDateTime orderTime;

    /**
     * 訂單狀態：0=草稿，1=已下單，2=已付款，3=已出貨，4=已完成，5=已取消
     */
    @Column(name = "status", columnDefinition = "TINYINT COMMENT '訂單狀態：0=草稿，1=已下單，2=已付款，3=已出貨，4=已完成，5=已取消'")
    @Schema(description = "訂單狀態", example = "PENDING")
    private OrderStatus status;

    /**
     * 訂單總額
     */
    @Column(name = "total_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) COMMENT '訂單總額'")
    @DecimalMin(value = "0.00", message = "訂單總額不能為負數")
    @Schema(description = "訂單總額", example = "1000.00")
    private BigDecimal totalAmount;

    /**
     * 折扣金額
     */
    @Column(name = "discount_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) COMMENT '折扣金額'")
    @DecimalMin(value = "0.00", message = "折扣金額不能為負數")
    @Schema(description = "折扣金額", example = "100.00")
    private BigDecimal discountAmount;

    /**
     * 實付金額
     */
    @Column(name = "final_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) COMMENT '實付金額'")
    @DecimalMin(value = "0.00", message = "實付金額不能為負數")
    @Schema(description = "實付金額", example = "900.00")
    private BigDecimal finalAmount;

    /**
     * 支付方式（現金、信用卡、LINE Pay…）
     */
    @Column(name = "payment_method", columnDefinition = "TINYINT COMMENT '支付方式（現金、信用卡、LINE Pay…）'")
    @Schema(description = "支付方式", example = "CASH")
    private PaymentMethod paymentMethod;

    /**
     * 備註（例如特殊需求、寵物名）
     */
    @Column(name = "notes", columnDefinition = "TEXT COMMENT '備註（例如特殊需求、寵物名）'")
    @Schema(description = "備註資訊", example = "寵物名：小白，特殊需求：需要溫柔對待")
    private String notes;

    /**
     * 建立時間
     */
    @Column(name = "created_at", columnDefinition = "DATETIME(3) COMMENT '建立時間'")
    @Schema(description = "建立時間", example = "2025-08-06T22:18:00")
    private LocalDateTime createdAt;

    /**
     * 更新時間
     */
    @Column(name = "updated_at", columnDefinition = "DATETIME(3) COMMENT '更新時間'")
    @Schema(description = "更新時間", example = "2025-08-06T22:18:00")
    private LocalDateTime updatedAt;

    /**
     * 在持久化之前設置創建時間
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (orderTime == null) {
            orderTime = now;
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