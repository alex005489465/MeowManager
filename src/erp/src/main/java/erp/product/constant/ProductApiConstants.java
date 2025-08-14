package erp.product.constant;

/**
 * 產品API路徑常量類
 * 統一管理產品相關的API路徑常量
 */
public class ProductApiConstants {

    //region API 路徑常量
    /**
     * 產品API基礎路徑
     */
    public static final String API_BASE_PATH = "/api/products";
    
    /**
     * 創建產品路徑
     */
    public static final String CREATE_PATH = "/create";
    
    /**
     * 更新產品路徑
     */
    public static final String UPDATE_PATH = "/update";
    
    /**
     * 更新產品狀態路徑
     */
    public static final String UPDATE_STATUS_PATH = "/updateStatus";
    
    /**
     * 根據ID查詢路徑
     */
    public static final String GET_BY_ID_PATH = "/getById";
    
    /**
     * 查詢所有產品路徑
     */
    public static final String GET_ALL_PATH = "/getAll";
    
    /**
     * 根據類型查詢路徑
     */
    public static final String GET_BY_TYPE_PATH = "/getByType";
    
    /**
     * 根據狀態查詢路徑
     */
    public static final String GET_BY_STATUS_PATH = "/getByStatus";
    
    /**
     * 根據名稱搜索路徑
     */
    public static final String SEARCH_BY_NAME_PATH = "/searchByName";
    
    /**
     * 多條件搜索路徑
     */
    public static final String SEARCH_PATH = "/search";
    
    /**
     * 根據價格範圍查詢路徑
     */
    public static final String GET_BY_PRICE_RANGE_PATH = "/getByPriceRange";
    
    /**
     * 產品統計路徑
     */
    public static final String GET_STATISTICS_PATH = "/getStatistics";
    //endregion

    //region 構造函數
    private ProductApiConstants() {
        // 私有構造函數，防止實例化
    }
    //endregion
}