package erp.order.constant;

/**
 * 訂單API路徑常量類
 * 統一管理訂單相關的API路徑常量
 */
public class OrderApiConstants {

    //region API 路徑常量
    /**
     * 訂單API基礎路徑
     */
    public static final String API_BASE_PATH = "/api/orders";
    
    /**
     * 創建訂單路徑
     */
    public static final String CREATE_PATH = "/create";
    
    /**
     * 更新訂單路徑
     */
    public static final String UPDATE_PATH = "/update";
    
    /**
     * 更新訂單狀態路徑
     */
    public static final String UPDATE_STATUS_PATH = "/update-status";
    
    /**
     * 根據ID查詢路徑
     */
    public static final String GET_BY_ID_PATH = "/get-by-id";
    
    /**
     * 查詢所有訂單路徑
     */
    public static final String GET_ALL_PATH = "/get-all";
    
    /**
     * 根據客戶查詢路徑
     */
    public static final String GET_BY_CUSTOMER_PATH = "/get-by-customer";
    
    /**
     * 根據狀態查詢路徑
     */
    public static final String GET_BY_STATUS_PATH = "/get-by-status";
    
    /**
     * 根據支付方式查詢路徑
     */
    public static final String GET_BY_PAYMENT_METHOD_PATH = "/get-by-payment-method";
    
    /**
     * 多條件搜索路徑
     */
    public static final String SEARCH_PATH = "/search";
    
    /**
     * 根據日期範圍查詢路徑
     */
    public static final String GET_BY_DATE_RANGE_PATH = "/get-by-date-range";
    
    /**
     * 根據金額範圍查詢路徑
     */
    public static final String GET_BY_AMOUNT_RANGE_PATH = "/get-by-amount-range";
    
    /**
     * 高級搜索路徑
     */
    public static final String ADVANCED_SEARCH_PATH = "/advanced-search";
    //endregion

    //region 構造函數
    private OrderApiConstants() {
        // 私有構造函數，防止實例化
    }
    //endregion
}