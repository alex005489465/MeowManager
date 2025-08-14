package erp.order.constant;

/**
 * 訂單業務常量類
 */
public class OrderConstants {
    
    //region 成功訊息常量
    /**
     * 訂單創建成功
     */
    public static final String ORDER_CREATE_SUCCESS = "訂單創建成功";
    
    /**
     * 訂單更新成功
     */
    public static final String ORDER_UPDATE_SUCCESS = "訂單更新成功";
    
    /**
     * 訂單狀態更新成功
     */
    public static final String ORDER_STATUS_UPDATE_SUCCESS = "訂單狀態更新成功";
    
    /**
     * 訂單取消成功
     */
    public static final String ORDER_CANCEL_SUCCESS = "訂單取消成功";
    //endregion

    //region 錯誤訊息常量
    /**
     * 訂單不存在錯誤訊息
     */
    public static final String ORDER_NOT_FOUND = "訂單不存在，ID: ";
    
    /**
     * 根據訂單編號找不到訂單錯誤訊息
     */
    public static final String ORDER_NOT_FOUND_BY_NO = "訂單不存在，編號: ";
    
    /**
     * 客戶不存在錯誤訊息
     */
    public static final String CUSTOMER_NOT_FOUND = "客戶不存在";
    
    /**
     * 商品不存在錯誤訊息
     */
    public static final String PRODUCT_NOT_FOUND = "商品不存在";
    
    /**
     * 訂單狀態不允許修改錯誤訊息
     */
    public static final String ORDER_STATUS_NOT_MODIFIABLE = "訂單狀態不允許修改";
    
    /**
     * 訂單不允許取消錯誤訊息
     */
    public static final String ORDER_CANNOT_BE_CANCELLED = "訂單不允許取消";
    
    /**
     * 訂單總額驗證失敗錯誤訊息
     */
    public static final String ORDER_TOTAL_AMOUNT_INVALID = "訂單總額驗證失敗";
    
    /**
     * 庫存不足錯誤訊息
     */
    public static final String INSUFFICIENT_STOCK = "庫存不足";
    //endregion

    //region 日誌常量
    /**
     * 創建訂單日誌
     */
    public static final String LOG_CREATE_ORDER = "創建訂單：{}";
    
    /**
     * 更新訂單日誌
     */
    public static final String LOG_UPDATE_ORDER = "更新訂單ID：{}";
    
    /**
     * 更新訂單狀態日誌
     */
    public static final String LOG_UPDATE_ORDER_STATUS = "更新訂單狀態，ID：{}，狀態：{}";
    
    /**
     * 取消訂單日誌
     */
    public static final String LOG_CANCEL_ORDER = "取消訂單ID：{}";
    
    /**
     * 查詢訂單錯誤日誌
     */
    public static final String LOG_ERROR_GET_ORDER = "查詢訂單發生錯誤：{}";
    
    /**
     * 庫存檢查日誌
     */
    public static final String LOG_STOCK_CHECK = "檢查商品庫存，商品ID：{}，需求數量：{}";
    
    /**
     * 庫存扣減日誌
     */
    public static final String LOG_STOCK_DEDUCT = "扣減商品庫存，商品ID：{}，扣減數量：{}";
    
    /**
     * 庫存回補日誌
     */
    public static final String LOG_STOCK_RESTORE = "回補商品庫存，商品ID：{}，回補數量：{}";
    //endregion

    //region 業務常量
    /**
     * 訂單編號前綴
     */
    public static final String ORDER_NO_PREFIX = "ORD";
    
    /**
     * 預設折扣金額
     */
    public static final String DEFAULT_DISCOUNT_AMOUNT = "0.00";
    //endregion

    //region 構造函數
    private OrderConstants() {
        // 私有構造函數，防止實例化
    }
    //endregion
}