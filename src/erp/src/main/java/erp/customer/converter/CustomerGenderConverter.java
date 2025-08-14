package erp.customer.converter;

import erp.common.converter.CodedEnumConverter;
import erp.customer.enums.CustomerGender;
import jakarta.persistence.Converter;

/**
 * CustomerGender 枚舉轉換器
 * 用於在資料庫中存儲自定義的整數代碼
 */
@Converter(autoApply = true)
public class CustomerGenderConverter extends CodedEnumConverter<CustomerGender> {

    public CustomerGenderConverter() {
        super(CustomerGender.class);
    }
}