package erp.customer.dto;

import erp.customer.enums.CustomerStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 客戶狀態更新請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatusUpdateRequest {
    private Long id;
    private CustomerStatus status;
}