package erp.product.converter;

import erp.common.converter.CodedEnumConverter;
import erp.product.enums.ProductType;
import jakarta.persistence.Converter;

/**
 * ProductType 枚舉轉換器
 * 用於在資料庫中存儲自定義的整數代碼
 */
@Converter(autoApply = true)
public class ProductTypeConverter extends CodedEnumConverter<ProductType> {
    public ProductTypeConverter() {
        super(ProductType.class);
    }
}