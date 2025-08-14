package erp.order.repository;

import erp.order.entity.OrderItem;
import erp.product.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 訂單明細資料存取層
 * 提供訂單明細相關的資料庫操作方法
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    //region 基本查詢方法
    /**
     * 根據訂單ID查詢所有訂單明細
     * @param orderId 訂單ID
     * @return 訂單明細列表
     */
    List<OrderItem> findByOrderId(Long orderId);
    
    /**
     * 根據訂單ID分頁查詢訂單明細
     * @param orderId 訂單ID
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    Page<OrderItem> findByOrderId(Long orderId, Pageable pageable);
    
    /**
     * 檢查指定訂單是否有明細
     * @param orderId 訂單ID
     * @return 是否存在明細
     */
    boolean existsByOrderId(Long orderId);
    
    /**
     * 統計指定訂單的明細數量
     * @param orderId 訂單ID
     * @return 明細數量
     */
    long countByOrderId(Long orderId);
    //endregion
    
    //region 按產品查詢
    /**
     * 根據產品ID查詢訂單明細
     * @param productId 產品ID
     * @return 訂單明細列表
     */
    List<OrderItem> findByProductId(Long productId);
    
    /**
     * 根據產品ID分頁查詢訂單明細
     * @param productId 產品ID
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    Page<OrderItem> findByProductId(Long productId, Pageable pageable);
    
    /**
     * 根據產品名稱查詢訂單明細
     * @param productName 產品名稱
     * @return 訂單明細列表
     */
    List<OrderItem> findByProductName(String productName);
    
    /**
     * 根據產品名稱模糊查詢訂單明細
     * @param productName 產品名稱關鍵字
     * @return 訂單明細列表
     */
    List<OrderItem> findByProductNameContainingIgnoreCase(String productName);
    
    /**
     * 檢查產品是否在任何訂單中被使用
     * @param productId 產品ID
     * @return 是否被使用
     */
    boolean existsByProductId(Long productId);
    //endregion
    
    //region 按產品類型查詢
    /**
     * 根據產品類型查詢訂單明細
     * @param productType 產品類型
     * @return 訂單明細列表
     */
    List<OrderItem> findByProductType(ProductType productType);
    
    /**
     * 根據產品類型分頁查詢訂單明細
     * @param productType 產品類型
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    Page<OrderItem> findByProductType(ProductType productType, Pageable pageable);
    //endregion
    
    //region 按數量範圍查詢
    /**
     * 根據數量範圍查詢訂單明細
     * @param minQty 最小數量
     * @param maxQty 最大數量
     * @return 訂單明細列表
     */
    List<OrderItem> findByQtyBetween(Integer minQty, Integer maxQty);
    
    /**
     * 根據數量範圍分頁查詢訂單明細
     * @param minQty 最小數量
     * @param maxQty 最大數量
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    Page<OrderItem> findByQtyBetween(Integer minQty, Integer maxQty, Pageable pageable);
    
    /**
     * 查詢數量大於等於指定值的訂單明細
     * @param qty 數量下限
     * @return 訂單明細列表
     */
    List<OrderItem> findByQtyGreaterThanEqual(Integer qty);
    
    /**
     * 查詢數量小於等於指定值的訂單明細
     * @param qty 數量上限
     * @return 訂單明細列表
     */
    List<OrderItem> findByQtyLessThanEqual(Integer qty);
    //endregion
    
    //region 按單價範圍查詢
    /**
     * 根據單價範圍查詢訂單明細
     * @param minPrice 最小單價
     * @param maxPrice 最大單價
     * @return 訂單明細列表
     */
    List<OrderItem> findByUnitPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * 根據單價範圍分頁查詢訂單明細
     * @param minPrice 最小單價
     * @param maxPrice 最大單價
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    Page<OrderItem> findByUnitPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    /**
     * 查詢單價大於等於指定值的訂單明細
     * @param price 單價下限
     * @return 訂單明細列表
     */
    List<OrderItem> findByUnitPriceGreaterThanEqual(BigDecimal price);
    
    /**
     * 查詢單價小於等於指定值的訂單明細
     * @param price 單價上限
     * @return 訂單明細列表
     */
    List<OrderItem> findByUnitPriceLessThanEqual(BigDecimal price);
    //endregion
    
    //region 按總額查詢
    /**
     * 根據明細總額範圍查詢（單價 × 數量）
     * @param minAmount 最小總額
     * @param maxAmount 最大總額
     * @return 訂單明細列表
     */
    @Query("SELECT oi FROM OrderItem oi WHERE (oi.unitPrice * oi.qty) BETWEEN :minAmount AND :maxAmount")
    List<OrderItem> findByTotalAmountBetween(@Param("minAmount") BigDecimal minAmount,
                                           @Param("maxAmount") BigDecimal maxAmount);
    
    /**
     * 根據明細總額範圍分頁查詢（單價 × 數量）
     * @param minAmount 最小總額
     * @param maxAmount 最大總額
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    @Query("SELECT oi FROM OrderItem oi WHERE (oi.unitPrice * oi.qty) BETWEEN :minAmount AND :maxAmount")
    Page<OrderItem> findByTotalAmountBetween(@Param("minAmount") BigDecimal minAmount,
                                           @Param("maxAmount") BigDecimal maxAmount,
                                           Pageable pageable);
    //endregion
    
    //region 排序查詢
    /**
     * 根據訂單ID查詢明細並按創建時間升序排列
     * @param orderId 訂單ID
     * @return 訂單明細列表
     */
    List<OrderItem> findByOrderIdOrderByCreatedAtAsc(Long orderId);
    
    /**
     * 根據訂單ID查詢明細並按創建時間降序排列
     * @param orderId 訂單ID
     * @return 訂單明細列表
     */
    List<OrderItem> findByOrderIdOrderByCreatedAtDesc(Long orderId);
    
    /**
     * 根據產品類型查詢明細並按單價升序排列
     * @param productType 產品類型
     * @return 訂單明細列表
     */
    List<OrderItem> findByProductTypeOrderByUnitPriceAsc(ProductType productType);
    
    /**
     * 根據產品類型查詢明細並按單價降序排列
     * @param productType 產品類型
     * @return 訂單明細列表
     */
    List<OrderItem> findByProductTypeOrderByUnitPriceDesc(ProductType productType);
    
    /**
     * 根據產品ID查詢明細並按數量降序排列
     * @param productId 產品ID
     * @return 訂單明細列表
     */
    List<OrderItem> findByProductIdOrderByQtyDesc(Long productId);
    //endregion
    
    //region 統計查詢
    /**
     * 按產品類型統計明細數量
     * @return 產品類型-數量對應列表
     */
    @Query("SELECT oi.productType, COUNT(oi) FROM OrderItem oi GROUP BY oi.productType")
    List<Object[]> countByProductType();
    
    /**
     * 按產品統計明細數量
     * @return 產品ID-數量對應列表
     */
    @Query("SELECT oi.productId, COUNT(oi) FROM OrderItem oi GROUP BY oi.productId")
    List<Object[]> countByProduct();
    
    /**
     * 按產品統計總數量
     * @return 產品ID-總數量對應列表
     */
    @Query("SELECT oi.productId, SUM(oi.qty) FROM OrderItem oi GROUP BY oi.productId")
    List<Object[]> sumQuantityByProduct();
    
    /**
     * 按產品統計總金額
     * @return 產品ID-總金額對應列表
     */
    @Query("SELECT oi.productId, SUM(oi.unitPrice * oi.qty) FROM OrderItem oi GROUP BY oi.productId")
    List<Object[]> sumAmountByProduct();
    
    /**
     * 按產品類型統計平均單價
     * @return 產品類型-平均單價對應列表
     */
    @Query("SELECT oi.productType, AVG(oi.unitPrice) FROM OrderItem oi GROUP BY oi.productType")
    List<Object[]> avgPriceByProductType();
    
    /**
     * 計算指定訂單的總金額
     * @param orderId 訂單ID
     * @return 總金額
     */
    @Query("SELECT SUM(oi.unitPrice * oi.qty) FROM OrderItem oi WHERE oi.orderId = :orderId")
    BigDecimal sumAmountByOrder(@Param("orderId") Long orderId);
    
    /**
     * 計算指定產品的總銷售數量
     * @param productId 產品ID
     * @return 總銷售數量
     */
    @Query("SELECT SUM(oi.qty) FROM OrderItem oi WHERE oi.productId = :productId")
    Long sumQuantityByProductId(@Param("productId") Long productId);
    
    /**
     * 計算指定產品類型的平均單價
     * @param productType 產品類型
     * @return 平均單價
     */
    @Query("SELECT AVG(oi.unitPrice) FROM OrderItem oi WHERE oi.productType = :productType")
    BigDecimal avgPriceByProductType(@Param("productType") ProductType productType);
    //endregion
    
    //region TOP查詢
    /**
     * 查詢單價最高的前10個明細
     * @return 訂單明細列表
     */
    List<OrderItem> findTop10ByOrderByUnitPriceDesc();
    
    /**
     * 查詢數量最多的前10個明細
     * @return 訂單明細列表
     */
    List<OrderItem> findTop10ByOrderByQtyDesc();
    
    /**
     * 查詢最新的前10個明細
     * @return 訂單明細列表
     */
    List<OrderItem> findTop10ByOrderByCreatedAtDesc();
    
    /**
     * 查詢指定產品類型中單價最高的前5個明細
     * @param productType 產品類型
     * @return 訂單明細列表
     */
    List<OrderItem> findTop5ByProductTypeOrderByUnitPriceDesc(ProductType productType);
    
    /**
     * 查詢指定訂單中數量最多的前5個明細
     * @param orderId 訂單ID
     * @return 訂單明細列表
     */
    List<OrderItem> findTop5ByOrderIdOrderByQtyDesc(Long orderId);
    //endregion
    
    //region 複合條件查詢
    /**
     * 多條件查詢訂單明細
     * @param orderId 訂單ID（可為null）
     * @param productId 產品ID（可為null）
     * @param productType 產品類型（可為null）
     * @param minQty 最小數量（可為null）
     * @param maxQty 最大數量（可為null）
     * @param minPrice 最小單價（可為null）
     * @param maxPrice 最大單價（可為null）
     * @param productName 產品名稱關鍵字（可為null）
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    @Query("SELECT oi FROM OrderItem oi WHERE " +
           "(:orderId IS NULL OR oi.orderId = :orderId) AND " +
           "(:productId IS NULL OR oi.productId = :productId) AND " +
           "(:productType IS NULL OR oi.productType = :productType) AND " +
           "(:minQty IS NULL OR oi.qty >= :minQty) AND " +
           "(:maxQty IS NULL OR oi.qty <= :maxQty) AND " +
           "(:minPrice IS NULL OR oi.unitPrice >= :minPrice) AND " +
           "(:maxPrice IS NULL OR oi.unitPrice <= :maxPrice) AND " +
           "(:productName IS NULL OR LOWER(oi.productName) LIKE LOWER(CONCAT('%', :productName, '%')))")
    Page<OrderItem> findByMultipleConditions(@Param("orderId") Long orderId,
                                            @Param("productId") Long productId,
                                            @Param("productType") ProductType productType,
                                            @Param("minQty") Integer minQty,
                                            @Param("maxQty") Integer maxQty,
                                            @Param("minPrice") BigDecimal minPrice,
                                            @Param("maxPrice") BigDecimal maxPrice,
                                            @Param("productName") String productName,
                                            Pageable pageable);
    //endregion
    
    //region 日期查詢
    /**
     * 根據創建日期範圍查詢訂單明細
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @return 訂單明細列表
     */
    List<OrderItem> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 根據創建日期範圍分頁查詢訂單明細
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    Page<OrderItem> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    /**
     * 根據更新日期範圍查詢訂單明細
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @return 訂單明細列表
     */
    List<OrderItem> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    //endregion
    
    //region 其他實用方法
    /**
     * 查詢有備註的訂單明細
     * @return 訂單明細列表
     */
    List<OrderItem> findByNotesIsNotNull();
    
    /**
     * 查詢沒有備註的訂單明細
     * @return 訂單明細列表
     */
    List<OrderItem> findByNotesIsNull();
    
    /**
     * 根據備註內容模糊查詢
     * @param notes 備註關鍵字
     * @return 訂單明細列表
     */
    List<OrderItem> findByNotesContainingIgnoreCase(String notes);
    
    /**
     * 查詢指定訂單和產品的明細
     * @param orderId 訂單ID
     * @param productId 產品ID
     * @return 訂單明細（可能為空）
     */
    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
    
    /**
     * 檢查指定訂單和產品是否已有明細
     * @param orderId 訂單ID
     * @param productId 產品ID
     * @return 是否存在明細
     */
    boolean existsByOrderIdAndProductId(Long orderId, Long productId);
    
    /**
     * 刪除指定訂單的所有明細
     * @param orderId 訂單ID
     */
    void deleteByOrderId(Long orderId);
    //endregion
}