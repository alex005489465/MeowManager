package erp.order.repository;

import erp.order.entity.Order;
import erp.order.enums.OrderStatus;
import erp.payment.enums.PaymentMethod;
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
 * 訂單資料存取層
 * 提供訂單相關的資料庫操作方法
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    //region 基本查詢方法
    /**
     * 根據訂單編號查詢訂單
     * @param orderNo 訂單編號
     * @return 訂單實體（Optional）
     */
    Optional<Order> findByNo(String orderNo);
    
    /**
     * 檢查訂單編號是否存在
     * @param orderNo 訂單編號
     * @return 是否存在
     */
    boolean existsByNo(String orderNo);
    
    /**
     * 檢查訂單編號是否存在（排除指定ID）
     * @param orderNo 訂單編號
     * @param id 要排除的訂單ID
     * @return 是否存在
     */
    boolean existsByNoAndIdNot(String orderNo, Long id);
    //endregion
    
    //region 按客戶查詢
    /**
     * 根據客戶ID查詢訂單
     * @param customerId 客戶ID
     * @return 訂單列表
     */
    List<Order> findByCustomerId(Long customerId);
    
    /**
     * 根據客戶ID分頁查詢訂單
     * @param customerId 客戶ID
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    Page<Order> findByCustomerId(Long customerId, Pageable pageable);
    //endregion
    
    //region 按狀態查詢
    /**
     * 根據訂單狀態查詢訂單
     * @param status 訂單狀態
     * @return 訂單列表
     */
    List<Order> findByStatus(OrderStatus status);
    
    /**
     * 根據訂單狀態分頁查詢訂單
     * @param status 訂單狀態
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    //endregion
    
    //region 按付款方式查詢
    /**
     * 根據付款方式查詢訂單
     * @param paymentMethod 付款方式
     * @return 訂單列表
     */
    List<Order> findByPaymentMethod(PaymentMethod paymentMethod);
    
    /**
     * 根據付款方式分頁查詢訂單
     * @param paymentMethod 付款方式
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    Page<Order> findByPaymentMethod(PaymentMethod paymentMethod, Pageable pageable);
    //endregion
    
    //region 按日期範圍查詢
    /**
     * 根據創建日期範圍查詢訂單
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @return 訂單列表
     */
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 根據創建日期範圍分頁查詢訂單
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    Page<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    /**
     * 根據更新日期範圍查詢訂單
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @return 訂單列表
     */
    List<Order> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    //endregion
    
    //region 按金額範圍查詢
    /**
     * 根據總金額範圍查詢訂單
     * @param minAmount 最小金額
     * @param maxAmount 最大金額
     * @return 訂單列表
     */
    List<Order> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    /**
     * 根據總金額範圍分頁查詢訂單
     * @param minAmount 最小金額
     * @param maxAmount 最大金額
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    Page<Order> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);
    
    /**
     * 根據最終金額範圍查詢訂單
     * @param minAmount 最小金額
     * @param maxAmount 最大金額
     * @return 訂單列表
     */
    List<Order> findByFinalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    /**
     * 根據最終金額範圍分頁查詢訂單
     * @param minAmount 最小金額
     * @param maxAmount 最大金額
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    Page<Order> findByFinalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);
    
    /**
     * 查詢金額大於等於指定值的訂單
     * @param amount 金額下限
     * @return 訂單列表
     */
    List<Order> findByFinalAmountGreaterThanEqual(BigDecimal amount);
    
    /**
     * 查詢金額小於等於指定值的訂單
     * @param amount 金額上限
     * @return 訂單列表
     */
    List<Order> findByFinalAmountLessThanEqual(BigDecimal amount);
    //endregion
    
    //region 排序查詢
    /**
     * 根據狀態查詢訂單並按創建時間升序排列
     * @param status 訂單狀態
     * @return 訂單列表
     */
    List<Order> findByStatusOrderByCreatedAtAsc(OrderStatus status);
    
    /**
     * 根據狀態查詢訂單並按創建時間降序排列
     * @param status 訂單狀態
     * @return 訂單列表
     */
    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
    
    /**
     * 根據客戶ID查詢訂單並按創建時間降序排列
     * @param customerId 客戶ID
     * @return 訂單列表
     */
    List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    //endregion
    
    //region 統計查詢
    /**
     * 按狀態統計訂單數量
     * @return 狀態-數量對應列表
     */
    @Query("SELECT o.status, COUNT(o) FROM Order o GROUP BY o.status")
    List<Object[]> countByStatus();
    
    /**
     * 按付款方式統計訂單數量
     * @return 付款方式-數量對應列表
     */
    @Query("SELECT o.paymentMethod, COUNT(o) FROM Order o GROUP BY o.paymentMethod")
    List<Object[]> countByPaymentMethod();
    
    /**
     * 按客戶統計訂單數量
     * @return 客戶ID-數量對應列表
     */
    @Query("SELECT o.customerId, COUNT(o) FROM Order o GROUP BY o.customerId")
    List<Object[]> countByCustomer();
    
    /**
     * 計算指定狀態訂單的平均金額
     * @param status 訂單狀態
     * @return 平均金額
     */
    @Query("SELECT AVG(o.finalAmount) FROM Order o WHERE o.status = :status")
    BigDecimal avgAmountByStatus(@Param("status") OrderStatus status);
    
    /**
     * 計算指定客戶訂單的總金額
     * @param customerId 客戶ID
     * @return 總金額
     */
    @Query("SELECT SUM(o.finalAmount) FROM Order o WHERE o.customerId = :customerId")
    BigDecimal sumAmountByCustomer(@Param("customerId") Long customerId);
    //endregion
    
    //region TOP查詢
    /**
     * 查詢金額最高的前10個訂單
     * @return 訂單列表
     */
    List<Order> findTop10ByOrderByFinalAmountDesc();
    
    /**
     * 查詢最新的前10個訂單
     * @return 訂單列表
     */
    List<Order> findTop10ByOrderByCreatedAtDesc();
    
    /**
     * 查詢指定客戶最新的前5個訂單
     * @param customerId 客戶ID
     * @return 訂單列表
     */
    List<Order> findTop5ByCustomerIdOrderByCreatedAtDesc(Long customerId);
    //endregion
    
    //region 複合條件查詢
    /**
     * 多條件查詢訂單
     * @param customerId 客戶ID（可為null）
     * @param status 訂單狀態（可為null）
     * @param paymentMethod 付款方式（可為null）
     * @param minAmount 最小金額（可為null）
     * @param maxAmount 最大金額（可為null）
     * @param startDate 開始日期（可為null）
     * @param endDate 結束日期（可為null）
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    @Query("SELECT o FROM Order o WHERE " +
           "(:customerId IS NULL OR o.customerId = :customerId) AND " +
           "(:status IS NULL OR o.status = :status) AND " +
           "(:paymentMethod IS NULL OR o.paymentMethod = :paymentMethod) AND " +
           "(:minAmount IS NULL OR o.finalAmount >= :minAmount) AND " +
           "(:maxAmount IS NULL OR o.finalAmount <= :maxAmount) AND " +
           "(:startDate IS NULL OR o.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR o.createdAt <= :endDate)")
    Page<Order> findByMultipleConditions(@Param("customerId") Long customerId,
                                        @Param("status") OrderStatus status,
                                        @Param("paymentMethod") PaymentMethod paymentMethod,
                                        @Param("minAmount") BigDecimal minAmount,
                                        @Param("maxAmount") BigDecimal maxAmount,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        Pageable pageable);
    //endregion
    
    //region 其他實用方法
    /**
     * 查詢有備註的訂單
     * @return 訂單列表
     */
    List<Order> findByNotesIsNotNull();
    
    /**
     * 查詢沒有備註的訂單
     * @return 訂單列表
     */
    List<Order> findByNotesIsNull();
    
    /**
     * 根據訂單編號模糊查詢
     * @param orderNo 訂單編號關鍵字
     * @return 訂單列表
     */
    List<Order> findByNoContainingIgnoreCase(String orderNo);
    //endregion
}