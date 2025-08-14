package erp.customer.dto;

import erp.common.dto.BasePageableRequest;
import erp.customer.enums.CustomerStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 客戶多條件搜索請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerSearchRequest extends BasePageableRequest {
    private String name;
    private String phone;
    private String email;
    private CustomerStatus status;
}