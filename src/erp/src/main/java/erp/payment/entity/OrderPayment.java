package erp.payment.entity;

import erp.payment.enums.PaymentMethod;
import erp.payment.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 訂單支付表實體類
 * 對應資料庫表：order_payments
 * 用來支持多支付方式（例如部分現金、部分信用卡）
 */
@Entity
@Schema(description = "訂單支付實體")
@Table(name = "order_payments", indexes = {
    @Index(name = "idx_order_id", columnList = "order_id"),
    @Index(name = "idx_payment_method", columnList = "payment_method"),
    @Index(name = "idx_paid_time", columnList = "paid_time")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPayment {

    /**
     * 支付記錄ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '支付記錄ID'")
    @Schema(description = "支付記錄ID", example = "1")
    private Long id;

    /**
     * 對應 orders.id
     */
    @Column(name = "order_id", nullable = false, columnDefinition = "BIGINT COMMENT '對應 orders.id'")
    @NotNull(message = "訂單ID不能為空")
    @Schema(description = "訂單ID", example = "1")
    private Long orderId;

    /**
     * 支付方式
     */
    @Column(name = "payment_method", length = 20, columnDefinition = "TINYINT COMMENT '支付方式'")
    @NotNull(message = "支付方式不能為空")
    @Schema(description = "支付方式", example = "CASH")
    private PaymentMethod paymentMethod;

    /**
     * 支付金額
     */
    @Column(name = "amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) COMMENT '支付金額'")
    @NotNull(message = "支付金額不能為空")
    @DecimalMin(value = "0.01", message = "支付金額必須大於0")
    @Schema(description = "支付金額", example = "500.00")
    private BigDecimal amount;

    /**
     * 支付時間
     */
    @Column(name = "paid_time", columnDefinition = "DATETIME(3) COMMENT '支付時間'")
    @NotNull(message = "支付時間不能為空")
    @Schema(description = "支付時間", example = "2025-08-06T22:18:00")
    private LocalDateTime paidTime;

    /**
     * 支付狀態
     */
    @Column(name = "status", columnDefinition = "TINYINT COMMENT '支付狀態：1=已支付，0=未支付，2=取消'")
    @Schema(description = "支付狀態", example = "PAID")
    private PaymentStatus status;

    /**
     * 支付備註（例如支付平台交易號）
     */
    @Column(name = "notes", columnDefinition = "TEXT COMMENT '支付備註（例如支付平台交易號）'")
    @Schema(description = "支付備註", example = "支付平台交易號: TXN123456789")
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
     * 在持久化之前設置創建時間和支付時間
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (paidTime == null) {
            paidTime = now;
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