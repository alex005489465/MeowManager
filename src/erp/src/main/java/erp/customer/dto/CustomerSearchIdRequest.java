package erp.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 客戶ID請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSearchIdRequest {
    private Long id;
}