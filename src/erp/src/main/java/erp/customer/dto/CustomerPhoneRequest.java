package erp.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 客戶手機號碼請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPhoneRequest {
    private String phone;
}