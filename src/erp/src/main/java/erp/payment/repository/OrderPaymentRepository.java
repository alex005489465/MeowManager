package erp.payment.repository;

import erp.payment.entity.OrderPayment;
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

/**
 * 訂單支付資料存取層
 */
@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {

    /**
     * 根據訂單ID查找支付記錄
     */
    List<OrderPayment> findByOrderId(Long orderId);

    /**
     * 根據訂單ID分頁查找支付記錄
     */
    Page<OrderPayment> findByOrderId(Long orderId, Pageable pageable);

    /**
     * 根據支付方式查找支付記錄
     */
    List<OrderPayment> findByPaymentMethod(PaymentMethod paymentMethod);

    /**
     * 根據支付方式分頁查找支付記錄
     */
    Page<OrderPayment> findByPaymentMethod(PaymentMethod paymentMethod, Pageable pageable);

    /**
     * 根據訂單ID和支付方式查找支付記錄
     */
    List<OrderPayment> findByOrderIdAndPaymentMethod(Long orderId, PaymentMethod paymentMethod);

    /**
     * 根據支付時間範圍查找支付記錄
     */
    List<OrderPayment> findByPaidTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根據支付時間範圍分頁查找支付記錄
     */
    Page<OrderPayment> findByPaidTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    /**
     * 根據支付金額範圍查找支付記錄
     */
    List<OrderPayment> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    /**
     * 根據支付金額範圍分頁查找支付記錄
     */
    Page<OrderPayment> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);

    /**
     * 根據多個條件搜索支付記錄
     */
    @Query("SELECT op FROM OrderPayment op WHERE " +
           "(:orderId IS NULL OR op.orderId = :orderId) AND " +
           "(:paymentMethod IS NULL OR op.paymentMethod = :paymentMethod) AND " +
           "(:minAmount IS NULL OR op.amount >= :minAmount) AND " +
           "(:maxAmount IS NULL OR op.amount <= :maxAmount) AND " +
           "(:startTime IS NULL OR op.paidTime >= :startTime) AND " +
           "(:endTime IS NULL OR op.paidTime <= :endTime)")
    Page<OrderPayment> findByMultipleConditions(
            @Param("orderId") Long orderId,
            @Param("paymentMethod") PaymentMethod paymentMethod,
            @Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable);

    /**
     * 根據訂單ID統計支付總額
     */
    @Query("SELECT SUM(op.amount) FROM OrderPayment op WHERE op.orderId = :orderId")
    BigDecimal sumAmountByOrderId(@Param("orderId") Long orderId);

    /**
     * 根據支付方式統計支付總額
     */
    @Query("SELECT SUM(op.amount) FROM OrderPayment op WHERE op.paymentMethod = :paymentMethod")
    BigDecimal sumAmountByPaymentMethod(@Param("paymentMethod") PaymentMethod paymentMethod);

    /**
     * 統計各支付方式的支付金額
     */
    @Query("SELECT op.paymentMethod, SUM(op.amount) FROM OrderPayment op GROUP BY op.paymentMethod")
    List<Object[]> sumAmountByPaymentMethod();

    /**
     * 統計各支付方式的支付次數
     */
    @Query("SELECT op.paymentMethod, COUNT(op) FROM OrderPayment op GROUP BY op.paymentMethod")
    List<Object[]> countByPaymentMethod();

    /**
     * 查找今日支付記錄
     */
    @Query("SELECT op FROM OrderPayment op WHERE DATE(op.paidTime) = CURRENT_DATE")
    List<OrderPayment> findTodayPayments();

    /**
     * 查找本月支付記錄
     */
    @Query("SELECT op FROM OrderPayment op WHERE YEAR(op.paidTime) = YEAR(CURRENT_DATE) AND MONTH(op.paidTime) = MONTH(CURRENT_DATE)")
    List<OrderPayment> findCurrentMonthPayments();

    /**
     * 統計今日各支付方式的收入
     */
    @Query("SELECT op.paymentMethod, SUM(op.amount) FROM OrderPayment op " +
           "WHERE DATE(op.paidTime) = CURRENT_DATE GROUP BY op.paymentMethod")
    List<Object[]> sumTodayAmountByPaymentMethod();

    /**
     * 統計本月各支付方式的收入
     */
    @Query("SELECT op.paymentMethod, SUM(op.amount) FROM OrderPayment op " +
           "WHERE YEAR(op.paidTime) = YEAR(CURRENT_DATE) AND MONTH(op.paidTime) = MONTH(CURRENT_DATE) " +
           "GROUP BY op.paymentMethod")
    List<Object[]> sumCurrentMonthAmountByPaymentMethod();

    /**
     * 查找最近的支付記錄
     */
    List<OrderPayment> findTop10ByOrderByPaidTimeDesc();

    /**
     * 根據時間範圍統計支付總額
     */
    @Query("SELECT SUM(op.amount) FROM OrderPayment op WHERE op.paidTime BETWEEN :startTime AND :endTime")
    BigDecimal sumAmountByPaidTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 根據時間範圍和支付方式統計支付總額
     */
    @Query("SELECT SUM(op.amount) FROM OrderPayment op WHERE op.paidTime BETWEEN :startTime AND :endTime AND op.paymentMethod = :paymentMethod")
    BigDecimal sumAmountByPaidTimeBetweenAndPaymentMethod(
            @Param("startTime") LocalDateTime startTime, 
            @Param("endTime") LocalDateTime endTime, 
            @Param("paymentMethod") PaymentMethod paymentMethod);

    /**
     * 檢查訂單是否有支付記錄
     */
    boolean existsByOrderId(Long orderId);

    /**
     * 根據訂單ID統計支付記錄數量
     */
    @Query("SELECT COUNT(op) FROM OrderPayment op WHERE op.orderId = :orderId")
    Long countByOrderId(@Param("orderId") Long orderId);
}