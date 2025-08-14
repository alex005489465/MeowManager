package erp.common.constant;

/**
 * API錯誤代碼常量
 */
public class ErrorCode {

    //region 通用錯誤
    /**
     * 不支援的操作類型
     */
    public static final String UNSUPPORTED_ACTION = "UNSUPPORTED_ACTION";

    /**
     * 參數錯誤或缺失
     */
    public static final String INVALID_ARGUMENT = "INVALID_ARGUMENT";
    //endregion

    //region 商品庫存相關錯誤
    /**
     * 商品不存在
     */
    public static final String ITEM_NOT_FOUND = "ITEM_NOT_FOUND";

    /**
     * 庫存不存在
     */
    public static final String STOCK_NOT_FOUND = "STOCK_NOT_FOUND";

    /**
     * 庫存不足
     */
    public static final String INSUFFICIENT_STOCK = "INSUFFICIENT_STOCK";
    //endregion

    //region 系統錯誤
    /**
     * 系統內部錯誤
     */
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";

    /**
     * 未預期的錯誤
     */
    public static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    //endregion

    //region 客戶相關錯誤
    /**
     * 客戶不存在
     */
    public static final String CUSTOMER_NOT_FOUND = "CUSTOMER_NOT_FOUND";

    /**
     * 客戶已存在
     */
    public static final String CUSTOMER_ALREADY_EXISTS = "CUSTOMER_ALREADY_EXISTS";

    /**
     * 手機號碼已存在
     */
    public static final String PHONE_ALREADY_EXISTS = "PHONE_ALREADY_EXISTS";

    /**
     * Email已存在
     */
    public static final String EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";

    /**
     * 電話號碼格式錯誤
     */
    public static final String INVALID_PHONE_FORMAT = "INVALID_PHONE_FORMAT";

    /**
     * 地址格式錯誤
     */
    public static final String INVALID_ADDRESS_FORMAT = "INVALID_ADDRESS_FORMAT";
    //endregion

    //region 訂單相關錯誤
    /**
     * 訂單不存在
     */
    public static final String ORDER_NOT_FOUND = "ORDER_NOT_FOUND";

    /**
     * 訂單已存在
     */
    public static final String ORDER_ALREADY_EXISTS = "ORDER_ALREADY_EXISTS";

    /**
     * 訂單狀態不允許修改
     */
    public static final String ORDER_STATUS_NOT_MODIFIABLE = "ORDER_STATUS_NOT_MODIFIABLE";

    /**
     * 訂單不允許取消
     */
    public static final String ORDER_CANNOT_BE_CANCELLED = "ORDER_CANNOT_BE_CANCELLED";

    /**
     * 訂單明細不存在
     */
    public static final String ORDER_ITEM_NOT_FOUND = "ORDER_ITEM_NOT_FOUND";

    /**
     * 訂單總額驗證失敗
     */
    public static final String ORDER_TOTAL_AMOUNT_INVALID = "ORDER_TOTAL_AMOUNT_INVALID";
    //endregion

    //region 構造函數
    private ErrorCode() {
        // 私有構造函數，防止實例化
    }
    //endregion
}