package erp.payment.constant;

/**
 * 付款API路徑常量類
 * 統一管理付款相關的API路徑常量
 */
public class PaymentApiConstants {

    //region API 路徑常量
    /**
     * 付款API基礎路徑
     */
    public static final String API_BASE_PATH = "/api/payments";
    
    /**
     * 處理付款路徑
     */
    public static final String PROCESS_PATH = "/process";
    
    /**
     * 取消付款路徑
     */
    public static final String CANCEL_PATH = "/cancel";
    
    /**
     * 根據ID查詢路徑
     */
    public static final String GET_BY_ID_PATH = "/get-by-id";
    
    /**
     * 根據訂單查詢路徑
     */
    public static final String GET_BY_ORDER_PATH = "/get-by-order";
    
    /**
     * 多條件搜索路徑
     */
    public static final String SEARCH_PATH = "/search";
    //endregion

    //region 構造函數
    private PaymentApiConstants() {
        // 私有構造函數，防止實例化
    }
    //endregion
}