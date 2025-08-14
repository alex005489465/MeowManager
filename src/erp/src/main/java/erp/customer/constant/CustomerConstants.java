package erp.customer.constant;

/**
 * 客戶模組常量類
 * 統一管理客戶相關的常量值
 */
public class CustomerConstants {

    //region 業務消息常量
    /**
     * 客戶創建成功消息
     */
    public static final String CUSTOMER_CREATE_SUCCESS = "客戶創建成功";
    
    /**
     * 客戶更新成功消息
     */
    public static final String CUSTOMER_UPDATE_SUCCESS = "客戶更新成功";
    
    /**
     * 客戶狀態更新成功消息
     */
    public static final String CUSTOMER_STATUS_UPDATE_SUCCESS = "客戶狀態更新成功";
    //endregion

    //region 驗證消息常量
    /**
     * 客戶姓名不能為空
     */
    public static final String NAME_NOT_BLANK = "客戶姓名不能為空";
    
    /**
     * 客戶ID不能為空
     */
    public static final String ID_NOT_NULL = "客戶ID不能為空";
    
    /**
     * 性別不能為空
     */
    public static final String GENDER_NOT_NULL = "性別不能為空";
    
    /**
     * 手機號碼不能為空
     */
    public static final String PHONE_NOT_BLANK = "手機號碼不能為空";
    
    /**
     * 地址不能為空
     */
    public static final String ADDRESS_NOT_BLANK = "地址不能為空";
    
    /**
     * 客戶姓名長度超限
     */
    public static final String NAME_LENGTH_EXCEEDED = "客戶姓名長度不能超過100個字符";
    
    /**
     * 姓名長度超限（用於DTO驗證）
     */
    public static final String NAME_SIZE_EXCEEDED = "姓名長度不能超過100個字符";
    
    /**
     * 暱稱長度超限
     */
    public static final String NICK_LENGTH_EXCEEDED = "暱稱長度不能超過100個字符";
    
    /**
     * Facebook帳號長度超限
     */
    public static final String FB_ACCOUNT_LENGTH_EXCEEDED = "Facebook帳號長度不能超過100個字符";
    
    /**
     * LINE帳號長度超限
     */
    public static final String LINE_ACCOUNT_LENGTH_EXCEEDED = "LINE帳號長度不能超過100個字符";
    
    /**
     * Email長度超限
     */
    public static final String EMAIL_LENGTH_EXCEEDED = "Email長度不能超過100個字符";
    
    /**
     * Email格式不正確
     */
    public static final String EMAIL_FORMAT_INVALID = "Email格式不正確";
    
    /**
     * 手機號碼長度超限
     */
    public static final String PHONE_LENGTH_EXCEEDED = "手機號碼長度不能超過30個字符";
    
    /**
     * 手機號碼格式錯誤
     */
    public static final String INVALID_PHONE_FORMAT = "手機號碼格式不正確，請使用台灣手機號碼格式（如：0912345678）";
    
    /**
     * 地址長度超限
     */
    public static final String ADDRESS_LENGTH_EXCEEDED = "地址長度不能超過200個字符";
    
    /**
     * 地址過短
     */
    public static final String ADDRESS_TOO_SHORT = "地址過短，請提供完整地址";
    
    /**
     * 地址格式不完整
     */
    public static final String INCOMPLETE_ADDRESS_FORMAT = "地址格式不完整，請提供包含縣市區域的完整地址";
    
    /**
     * 手機號碼已存在
     */
    public static final String PHONE_ALREADY_EXISTS = "手機號碼已存在: ";
    
    /**
     * Email已存在
     */
    public static final String EMAIL_ALREADY_EXISTS = "Email已存在: ";
    
    /**
     * 客戶不存在
     */
    public static final String CUSTOMER_NOT_FOUND = "客戶不存在，ID: ";
    //endregion

    //region 數值常量
    /**
     * 客戶姓名最大長度
     */
    public static final int NAME_MAX_LENGTH = 100;
    
    /**
     * 暱稱最大長度
     */
    public static final int NICK_MAX_LENGTH = 100;
    
    /**
     * Facebook帳號最大長度
     */
    public static final int FB_ACCOUNT_MAX_LENGTH = 100;
    
    /**
     * LINE帳號最大長度
     */
    public static final int LINE_ACCOUNT_MAX_LENGTH = 100;
    
    /**
     * Email最大長度
     */
    public static final int EMAIL_MAX_LENGTH = 100;
    
    /**
     * 手機號碼最大長度
     */
    public static final int PHONE_MAX_LENGTH = 30;
    
    /**
     * 地址最大長度
     */
    public static final int ADDRESS_MAX_LENGTH = 200;
    
    /**
     * 地址最小長度
     */
    public static final int ADDRESS_MIN_LENGTH = 5;
    
    /**
     * 最近客戶查詢數量
     */
    public static final int RECENT_CUSTOMERS_LIMIT = 10;
    //endregion

    //region 正則表達式常量
    /**
     * 台灣手機號碼正則表達式
     */
    public static final String TAIWAN_PHONE_REGEX = "^(09\\d{8}|\\+886-?9\\d{8}|886-?9\\d{8})$";
    //endregion

    //region 地址關鍵字常量
    /**
     * 地址驗證關鍵字
     */
    public static final String[] ADDRESS_KEYWORDS = {
        "市", "縣", "區", "鄉", "鎮", "里", "路", "街", "巷", "弄", "號"
    };
    //endregion

    //region 日誌消息常量
    /**
     * 創建客戶日誌
     */
    public static final String LOG_CREATE_CUSTOMER = "創建客戶: {}";
    
    /**
     * 客戶創建成功日誌
     */
    public static final String LOG_CUSTOMER_CREATE_SUCCESS = "客戶創建成功，ID: {}";
    
    /**
     * 更新客戶日誌
     */
    public static final String LOG_UPDATE_CUSTOMER = "更新客戶，ID: {}";
    
    /**
     * 客戶更新成功日誌
     */
    public static final String LOG_CUSTOMER_UPDATE_SUCCESS = "客戶更新成功，ID: {}";
    
    /**
     * 更新客戶狀態日誌
     */
    public static final String LOG_UPDATE_CUSTOMER_STATUS = "更新客戶狀態，ID: {}, 狀態: {}";
    
    /**
     * 客戶狀態更新成功日誌
     */
    public static final String LOG_CUSTOMER_STATUS_UPDATE_SUCCESS = "客戶狀態更新成功，ID: {}";
    
    /**
     * 刪除客戶日誌
     */
    public static final String LOG_DELETE_CUSTOMER = "刪除客戶，ID: {}";
    
    /**
     * 客戶刪除成功日誌
     */
    public static final String LOG_CUSTOMER_DELETE_SUCCESS = "客戶刪除成功，ID: {}";
    //endregion

    //region 錯誤日誌消息常量
    /**
     * 根據手機號碼查找客戶錯誤日誌
     */
    public static final String LOG_ERROR_GET_BY_PHONE = "根據手機號碼查找客戶時發生錯誤，手機: {}";
    
    /**
     * 根據Email查找客戶錯誤日誌
     */
    public static final String LOG_ERROR_GET_BY_EMAIL = "根據Email查找客戶時發生錯誤，Email: {}";
    
    /**
     * 根據姓名搜索客戶錯誤日誌
     */
    public static final String LOG_ERROR_SEARCH_BY_NAME = "根據姓名搜索客戶時發生錯誤，姓名: {}";
    
    /**
     * 根據狀態查找客戶錯誤日誌
     */
    public static final String LOG_ERROR_GET_BY_STATUS = "根據狀態查找客戶時發生錯誤，狀態: {}";
    
    /**
     * 獲取客戶狀態統計錯誤日誌
     */
    public static final String LOG_ERROR_STATUS_STATISTICS = "獲取客戶狀態統計時發生錯誤";
    
    /**
     * 多條件搜索客戶錯誤日誌
     */
    public static final String LOG_ERROR_MULTI_SEARCH = "多條件搜索客戶時發生錯誤";
    
    /**
     * 根據出生日期範圍查找客戶錯誤日誌
     */
    public static final String LOG_ERROR_BIRTH_DATE_RANGE = "根據出生日期範圍查找客戶時發生錯誤，開始日期: {}, 結束日期: {}";
    
    /**
     * 獲取最近註冊客戶錯誤日誌
     */
    public static final String LOG_ERROR_RECENT_CUSTOMERS = "獲取最近註冊客戶時發生錯誤";
    //endregion

    //region 構造函數
    private CustomerConstants() {
        // 私有構造函數，防止實例化
    }
    //endregion
}