package erp.customer.dto;

import erp.common.dto.BasePageableRequest;
import erp.customer.enums.CustomerStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 客戶狀態請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerSearchStatusRequest extends BasePageableRequest {
    private CustomerStatus status;
}