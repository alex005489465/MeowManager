package erp.customer.dto;

import erp.customer.constant.CustomerConstants;
import erp.customer.entity.Customer;
import erp.customer.enums.CustomerGender;
import erp.customer.validation.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * 客戶更新請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "客戶更新請求")
public class CustomerUpdateRequest {

    /**
     * 客戶ID
     */
    @NotNull(message = CustomerConstants.ID_NOT_NULL)
    @Schema(description = "客戶ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 姓名
     */
    @NotBlank(message = CustomerConstants.NAME_NOT_BLANK)
    @Size(max = CustomerConstants.NAME_MAX_LENGTH, message = CustomerConstants.NAME_SIZE_EXCEEDED)
    @Schema(description = "客戶姓名", example = "張三", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 100)
    private String name;

    /**
     * 暱稱
     */
    @Size(max = CustomerConstants.NICK_MAX_LENGTH, message = CustomerConstants.NICK_LENGTH_EXCEEDED)
    @Schema(description = "客戶暱稱", example = "小張", maxLength = 100)
    private String nick;

    /**
     * 性別：1=男、2=女、3=其他
     */
    @NotNull(message = CustomerConstants.GENDER_NOT_NULL)
    @Schema(description = "客戶性別", example = "MALE", requiredMode = Schema.RequiredMode.REQUIRED)
    private CustomerGender gender;

    /**
     * 出生日期
     */
    @Schema(description = "出生日期", example = "1990-01-01")
    private LocalDate birthDate;

    /**
     * Facebook帳號
     */
    @Size(max = CustomerConstants.FB_ACCOUNT_MAX_LENGTH, message = CustomerConstants.FB_ACCOUNT_LENGTH_EXCEEDED)
    @Schema(description = "Facebook帳號", example = "john.doe", maxLength = 100)
    private String fbAccount;

    /**
     * LINE帳號
     */
    @Size(max = CustomerConstants.LINE_ACCOUNT_MAX_LENGTH, message = CustomerConstants.LINE_ACCOUNT_LENGTH_EXCEEDED)
    @Schema(description = "LINE帳號", example = "john_line", maxLength = 100)
    private String lineAccount;

    /**
     * Email
     */
    @Size(max = CustomerConstants.EMAIL_MAX_LENGTH, message = CustomerConstants.EMAIL_LENGTH_EXCEEDED)
    @Email(message = CustomerConstants.EMAIL_FORMAT_INVALID)
    @Schema(description = "電子郵件地址", example = "john.doe@example.com", maxLength = 100)
    private String email;

    /**
     * 手機
     */
    @NotBlank(message = CustomerConstants.PHONE_NOT_BLANK)
    @Size(max = CustomerConstants.PHONE_MAX_LENGTH, message = CustomerConstants.PHONE_LENGTH_EXCEEDED)
    @PhoneNumber()
    @Schema(description = "手機號碼", example = "0912345678", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 30)
    private String phone;

    /**
     * 地址
     */
    @NotBlank(message = CustomerConstants.ADDRESS_NOT_BLANK)
    @Size(max = CustomerConstants.ADDRESS_MAX_LENGTH, message = CustomerConstants.ADDRESS_LENGTH_EXCEEDED)
    @Schema(description = "聯絡地址", example = "台北市信義區信義路五段7號", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 500)
    private String address;

    /**
     * 備註
     */
    @Schema(description = "備註資訊", example = "VIP客戶")
    private String note;
}