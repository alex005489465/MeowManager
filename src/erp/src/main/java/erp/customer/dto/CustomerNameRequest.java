package erp.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 客戶姓名請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerNameRequest {
    private String name;
}