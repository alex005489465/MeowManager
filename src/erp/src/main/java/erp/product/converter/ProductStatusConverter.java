package erp.product.converter;

import erp.common.converter.CodedEnumConverter;
import erp.product.enums.ProductStatus;
import jakarta.persistence.Converter;

/**
 * ProductStatus 枚舉轉換器
 * 用於在資料庫中存儲自定義的整數代碼
 */
@Converter(autoApply = true)
public class ProductStatusConverter extends CodedEnumConverter<ProductStatus> {

    public ProductStatusConverter() {
        super(ProductStatus.class);
    }
}