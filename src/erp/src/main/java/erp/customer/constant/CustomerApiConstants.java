package erp.customer.constant;

/**
 * 客戶API路徑常量類
 * 統一管理客戶相關的API路徑常量
 */
public class CustomerApiConstants {

    //region API 路徑常量
    /**
     * 客戶API基礎路徑
     */
    public static final String API_BASE_PATH = "/api/customers";
    
    /**
     * 創建客戶路徑
     */
    public static final String CREATE_PATH = "/create";
    
    /**
     * 更新客戶路徑
     */
    public static final String UPDATE_PATH = "/update";
    
    /**
     * 根據ID查詢路徑
     */
    public static final String GET_BY_ID_PATH = "/getById";
    
    /**
     * 查詢所有客戶路徑
     */
    public static final String GET_ALL_PATH = "/getAll";
    
    /**
     * 根據手機號查詢路徑
     */
    public static final String GET_BY_PHONE_PATH = "/getByPhone";
    
    /**
     * 根據Email查詢路徑
     */
    public static final String GET_BY_EMAIL_PATH = "/getByEmail";
    
    /**
     * 根據姓名搜索路徑
     */
    public static final String SEARCH_BY_NAME_PATH = "/searchByName";
    
    /**
     * 根據狀態查詢路徑
     */
    public static final String GET_BY_STATUS_PATH = "/getByStatus";
    
    /**
     * 狀態統計路徑
     */
    public static final String STATUS_STATISTICS_PATH = "/getStatusStatistics";
    
    /**
     * 更新狀態路徑
     */
    public static final String UPDATE_STATUS_PATH = "/updateStatus";
    
    /**
     * 多條件搜索路徑
     */
    public static final String SEARCH_PATH = "/search";
    
    /**
     * 根據出生日期範圍查詢路徑
     */
    public static final String GET_BY_BIRTH_DATE_RANGE_PATH = "/getByBirthDateRange";
    
    /**
     * 獲取最近註冊客戶路徑
     */
    public static final String GET_RECENT_PATH = "/getRecent";
    //endregion

    //region 構造函數
    private CustomerApiConstants() {
        // 私有構造函數，防止實例化
    }
    //endregion
}