package erp.common.converter;

import erp.common.enums.CodedEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 抽象基類，用於所有實現 CodedEnum 接口的枚舉轉換器
 * 提供統一的數據庫轉換邏輯
 *
 * @param <T> 實現 CodedEnum 接口的枚舉類型
 */
@Converter
public abstract class CodedEnumConverter<T extends Enum<T> & CodedEnum>
        implements AttributeConverter<T, Integer> {

    private final Class<T> enumClass;

    /**
     * 構造函數
     * @param enumClass 枚舉類的 Class 對象
     */
    protected CodedEnumConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    /**
     * 將枚舉轉換為數據庫列值
     * @param attribute 枚舉值
     * @return 對應的 int 值，如果枚舉為 null 則返回 null
     */
    @Override
    public Integer convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    /**
     * 將數據庫列值轉換為枚舉
     * @param dbData 數據庫中的 int 值
     * @return 對應的枚舉值，如果數據庫值為 null 則返回 null
     */
    @Override
    public T convertToEntityAttribute(Integer dbData) {
        return dbData != null ? CodedEnum.fromCode(enumClass, dbData) : null;
    }
}