package erp.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 客戶Email請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEmailRequest {
    private String email;
}