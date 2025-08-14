package erp.customer.converter;

import erp.common.converter.CodedEnumConverter;
import erp.customer.enums.CustomerStatus;
import jakarta.persistence.Converter;

/**
 * CustomerStatus 枚舉轉換器
 * 用於在資料庫中存儲自定義的整數代碼
 */
@Converter(autoApply = true)
public class CustomerStatusConverter extends CodedEnumConverter<CustomerStatus> {

    public CustomerStatusConverter() {
        super(CustomerStatus.class);
    }
}