package erp.common.enums;

/**
 * 統一枚舉接口
 * 所有需要與數據庫TINYINT字段映射的枚舉都應實現此接口
 */
public interface CodedEnum {
    /**
     * 獲取枚舉的代碼值，統一使用 int 匹配 TINYINT
     * @return 枚舉代碼
     */
    int getCode();

    /**
     * 獲取枚舉的描述
     * @return 枚舉描述
     */
    String getDescription();

    /**
     * 提供通用的轉換方法，根據代碼獲取枚舉值
     * @param enumClass 枚舉類
     * @param code 代碼值
     * @param <T> 枚舉類型
     * @return 對應的枚舉值
     * @throws IllegalArgumentException 當代碼不存在時拋出異常
     */
    static <T extends Enum<T> & CodedEnum> T fromCode(Class<T> enumClass, int code) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getCode() == code) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code + " for enum: " + enumClass.getSimpleName());
    }
}