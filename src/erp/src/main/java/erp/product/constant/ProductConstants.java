package erp.product.constant;

/**
 * 產品業務常量類
 */
public class ProductConstants {
    
    //region 成功訊息常量
    /**
     * 產品創建成功
     */
    public static final String PRODUCT_CREATE_SUCCESS = "產品創建成功";
    
    /**
     * 產品更新成功
     */
    public static final String PRODUCT_UPDATE_SUCCESS = "產品更新成功";
    
    /**
     * 產品狀態更新成功
     */
    public static final String PRODUCT_STATUS_UPDATE_SUCCESS = "產品狀態更新成功";
    //endregion

    //region 錯誤訊息常量
    /**
     * 產品不存在錯誤訊息
     */
    public static final String PRODUCT_NOT_FOUND = "產品不存在";
    
    /**
     * 產品名稱重複錯誤訊息
     */
    public static final String PRODUCT_NAME_DUPLICATE = "產品名稱已存在";
    //endregion

    //region 日誌常量
    /**
     * 創建產品日誌
     */
    public static final String LOG_CREATE_PRODUCT = "創建產品：{}";
    
    /**
     * 更新產品日誌
     */
    public static final String LOG_UPDATE_PRODUCT = "更新產品ID：{}";
    
    /**
     * 查詢產品錯誤日誌
     */
    public static final String LOG_ERROR_GET_PRODUCT = "查詢產品發生錯誤：{}";
    //endregion

    //region 構造函數
    private ProductConstants() {
        // 私有構造函數，防止實例化
    }
    //endregion
}