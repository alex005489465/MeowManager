package erp.customer.entity;

import erp.customer.enums.CustomerGender;
import erp.customer.enums.CustomerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 客戶資料表實體類
 * 對應資料庫表：customers
 */
@Entity
@Schema(description = "客戶實體")
@Table(name = "customers", indexes = {
    @Index(name = "idx_name", columnList = "name"),
    @Index(name = "idx_phone", columnList = "phone"),
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    /**
     * 客戶ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '客戶ID'")
    @Schema(description = "客戶ID", example = "1")
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "name", length = 100, columnDefinition = "VARCHAR(100) COMMENT '姓名'")
    @Size(max = 100, message = "姓名長度不能超過100個字符")
    @Schema(description = "客戶姓名", example = "張三", maxLength = 100)
    private String name;

    /**
     * 暱稱
     */
    @Column(name = "nick", length = 100, columnDefinition = "VARCHAR(100) COMMENT '暱稱'")
    @Size(max = 100, message = "暱稱長度不能超過100個字符")
    @Schema(description = "客戶暱稱", example = "小張", maxLength = 100)
    private String nick;

    /**
     * 性別：1=男、2=女、3=其他
     */
    @Column(name = "gender", columnDefinition = "TINYINT COMMENT '性別：1=男、2=女、3=其他'")
    @Schema(description = "客戶性別", example = "MALE")
    private CustomerGender gender;

    /**
     * 出生日期
     */
    @Column(name = "birth_date", columnDefinition = "DATE COMMENT '出生日期'")
    @Schema(description = "出生日期", example = "1990-01-01")
    private LocalDate birthDate;

    /**
     * Facebook帳號
     */
    @Column(name = "fb_account", length = 100, columnDefinition = "VARCHAR(100) COMMENT 'Facebook帳號'")
    @Size(max = 100, message = "Facebook帳號長度不能超過100個字符")
    @Schema(description = "Facebook帳號", example = "john.doe", maxLength = 100)
    private String fbAccount;

    /**
     * LINE帳號
     */
    @Column(name = "line_account", length = 100, columnDefinition = "VARCHAR(100) COMMENT 'LINE帳號'")
    @Size(max = 100, message = "LINE帳號長度不能超過100個字符")
    @Schema(description = "LINE帳號", example = "john_line", maxLength = 100)
    private String lineAccount;

    /**
     * Email
     */
    @Column(name = "email", length = 100, columnDefinition = "VARCHAR(100) COMMENT 'Email'")
    @Size(max = 100, message = "Email長度不能超過100個字符")
    @Email(message = "Email格式不正確")
    @Schema(description = "電子郵件地址", example = "john.doe@example.com", maxLength = 100)
    private String email;

    /**
     * 手機
     */
    @Column(name = "phone", length = 30, columnDefinition = "VARCHAR(30) COMMENT '手機'")
    @Size(max = 30, message = "手機號碼長度不能超過30個字符")
    @Schema(description = "手機號碼", example = "0912345678", maxLength = 30)
    private String phone;

    /**
     * 地址
     */
    @Column(name = "address", columnDefinition = "TEXT COMMENT '地址'")
    @Schema(description = "聯絡地址", example = "台北市信義區信義路五段7號")
    private String address;

    /**
     * 備註
     */
    @Column(name = "note", columnDefinition = "TEXT COMMENT '備註'")
    @Schema(description = "備註資訊", example = "VIP客戶")
    private String note;

    /**
     * 狀態(1=啟用、2=暫停、3=黑名單)
     */
    @Column(name = "status", columnDefinition = "TINYINT COMMENT '狀態(1=啟用、2=暫停、3=黑名單)'")
    @Schema(description = "客戶狀態", example = "ACTIVE")
    private CustomerStatus status;

    /**
     * 建立時間
     */
    @Column(name = "created_at", columnDefinition = "DATETIME(3) COMMENT '建立時間'")
    @Schema(description = "建立時間", example = "2023-01-01T10:00:00")
    private LocalDateTime createdAt;

    /**
     * 最後更新時間
     */
    @Column(name = "updated_at", columnDefinition = "DATETIME(3) COMMENT '最後更新時間'")
    @Schema(description = "最後更新時間", example = "2023-01-01T10:00:00")
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