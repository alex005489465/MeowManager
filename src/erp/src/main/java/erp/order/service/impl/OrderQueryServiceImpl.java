package erp.order.service.impl;

import erp.common.constant.ErrorCode;
import erp.common.exception.OrderException;
import erp.order.constant.OrderConstants;
import erp.order.entity.Order;
import erp.order.enums.OrderStatus;
import erp.order.repository.OrderRepository;
import erp.payment.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 訂單查詢功能實現
 * 專門處理訂單相關的查詢業務邏輯
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderQueryServiceImpl {

    private final OrderRepository orderRepository;

    /**
     * 根據ID查找訂單
     * @param id 訂單ID
     * @return 訂單實體
     * @throws OrderException 當訂單不存在時
     */
    public Order getOrderById(Long id) {
        log.debug("查詢訂單，ID: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderException(OrderConstants.ORDER_NOT_FOUND + id, ErrorCode.ORDER_NOT_FOUND));
    }

    /**
     * 根據訂單編號查找訂單
     * @param orderNo 訂單編號
     * @return 訂單實體
     * @throws OrderException 當訂單不存在時
     */
    public Order getOrderByNo(String orderNo) {
        log.debug("查詢訂單，編號: {}", orderNo);
        return orderRepository.findByNo(orderNo)
                .orElseThrow(() -> new OrderException(OrderConstants.ORDER_NOT_FOUND_BY_NO + orderNo, ErrorCode.ORDER_NOT_FOUND));
    }

    /**
     * 獲取所有訂單（分頁）
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    public Page<Order> getAllOrders(Pageable pageable) {
        log.debug("查詢所有訂單，分頁參數: {}", pageable);
        return orderRepository.findAll(pageable);
    }

    /**
     * 根據客戶ID查找訂單
     * @param customerId 客戶ID
     * @return 訂單列表
     */
    public List<Order> getOrdersByCustomerId(Long customerId) {
        log.debug("查詢客戶訂單，客戶ID: {}", customerId);
        return orderRepository.findByCustomerId(customerId);
    }

    /**
     * 根據訂單狀態查找訂單
     * @param status 訂單狀態
     * @return 訂單列表
     */
    public List<Order> getOrdersByStatus(OrderStatus status) {
        log.debug("查詢訂單，狀態: {}", status);
        return orderRepository.findByStatus(status);
    }

    /**
     * 根據付款方式查找訂單
     * @param paymentMethod 付款方式
     * @return 訂單列表
     */
    public List<Order> getOrdersByPaymentMethod(PaymentMethod paymentMethod) {
        log.debug("查詢訂單，付款方式: {}", paymentMethod);
        return orderRepository.findByPaymentMethod(paymentMethod);
    }

    /**
     * 根據日期範圍查找訂單
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @return 訂單列表
     */
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("查詢訂單，日期範圍: {} - {}", startDate, endDate);
        return orderRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * 根據金額範圍查找訂單
     * @param minAmount 最小金額
     * @param maxAmount 最大金額
     * @return 訂單列表
     */
    public List<Order> getOrdersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        log.debug("查詢訂單，金額範圍: {} - {}", minAmount, maxAmount);
        return orderRepository.findByFinalAmountBetween(minAmount, maxAmount);
    }

    /**
     * 根據客戶ID分頁查找訂單
     * @param customerId 客戶ID
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    public Page<Order> getOrdersByCustomerId(Long customerId, Pageable pageable) {
        log.debug("分頁查詢客戶訂單，客戶ID: {}, 分頁參數: {}", customerId, pageable);
        return orderRepository.findByCustomerId(customerId, pageable);
    }

    /**
     * 根據狀態分頁查找訂單
     * @param status 訂單狀態
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    public Page<Order> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        log.debug("分頁查詢訂單，狀態: {}, 分頁參數: {}", status, pageable);
        return orderRepository.findByStatus(status, pageable);
    }

    /**
     * 根據付款方式分頁查找訂單
     * @param paymentMethod 付款方式
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    public Page<Order> getOrdersByPaymentMethod(PaymentMethod paymentMethod, Pageable pageable) {
        log.debug("分頁查詢訂單，付款方式: {}, 分頁參數: {}", paymentMethod, pageable);
        return orderRepository.findByPaymentMethod(paymentMethod, pageable);
    }

    /**
     * 根據日期範圍分頁查找訂單
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    public Page<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.debug("分頁查詢訂單，日期範圍: {} - {}, 分頁參數: {}", startDate, endDate, pageable);
        return orderRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    /**
     * 根據金額範圍分頁查找訂單
     * @param minAmount 最小金額
     * @param maxAmount 最大金額
     * @param pageable 分頁參數
     * @return 訂單分頁結果
     */
    public Page<Order> getOrdersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable) {
        log.debug("分頁查詢訂單，金額範圍: {} - {}, 分頁參數: {}", minAmount, maxAmount, pageable);
        return orderRepository.findByFinalAmountBetween(minAmount, maxAmount, pageable);
    }

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
    public Page<Order> searchOrders(Long customerId, OrderStatus status, PaymentMethod paymentMethod,
                                   BigDecimal minAmount, BigDecimal maxAmount, 
                                   LocalDateTime startDate, LocalDateTime endDate, 
                                   Pageable pageable) {
        log.debug("多條件查詢訂單 - 客戶ID: {}, 狀態: {}, 付款方式: {}, 金額範圍: {}-{}, 日期範圍: {}-{}", 
                customerId, status, paymentMethod, minAmount, maxAmount, startDate, endDate);
        return orderRepository.findByMultipleConditions(customerId, status, paymentMethod, 
                                                       minAmount, maxAmount, startDate, endDate, pageable);
    }

    /**
     * 檢查訂單編號是否存在
     * @param orderNo 訂單編號
     * @return 是否存在
     */
    public boolean existsByOrderNo(String orderNo) {
        log.debug("檢查訂單編號是否存在: {}", orderNo);
        return orderRepository.existsByNo(orderNo);
    }

    /**
     * 檢查訂單編號是否存在（排除指定ID）
     * @param orderNo 訂單編號
     * @param excludeId 要排除的訂單ID
     * @return 是否存在
     */
    public boolean existsByOrderNoExcludingId(String orderNo, Long excludeId) {
        log.debug("檢查訂單編號是否存在（排除ID {}）: {}", excludeId, orderNo);
        return orderRepository.existsByNoAndIdNot(orderNo, excludeId);
    }

    /**
     * 獲取最近的訂單
     * @return 最近10個訂單
     */
    public List<Order> getRecentOrders() {
        log.debug("查詢最近訂單");
        return orderRepository.findTop10ByOrderByCreatedAtDesc();
    }

    /**
     * 獲取金額最高的訂單
     * @return 金額最高的10個訂單
     */
    public List<Order> getHighestAmountOrders() {
        log.debug("查詢金額最高訂單");
        return orderRepository.findTop10ByOrderByFinalAmountDesc();
    }

    /**
     * 獲取指定客戶的最近訂單
     * @param customerId 客戶ID
     * @return 該客戶最近5個訂單
     */
    public List<Order> getRecentOrdersByCustomer(Long customerId) {
        log.debug("查詢客戶最近訂單，客戶ID: {}", customerId);
        return orderRepository.findTop5ByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    /**
     * 統計各狀態的訂單數量
     * @return 狀態-數量對應列表
     */
    public List<Object[]> getOrderStatusStatistics() {
        log.debug("統計訂單狀態分佈");
        return orderRepository.countByStatus();
    }

    /**
     * 統計各付款方式的訂單數量
     * @return 付款方式-數量對應列表
     */
    public List<Object[]> getPaymentMethodStatistics() {
        log.debug("統計付款方式分佈");
        return orderRepository.countByPaymentMethod();
    }

    /**
     * 統計各客戶的訂單數量
     * @return 客戶ID-數量對應列表
     */
    public List<Object[]> getCustomerOrderStatistics() {
        log.debug("統計客戶訂單分佈");
        return orderRepository.countByCustomer();
    }

    /**
     * 計算指定狀態訂單的平均金額
     * @param status 訂單狀態
     * @return 平均金額
     */
    public BigDecimal getAverageAmountByStatus(OrderStatus status) {
        log.debug("計算平均金額，狀態: {}", status);
        return orderRepository.avgAmountByStatus(status);
    }

    /**
     * 計算指定客戶的訂單總金額
     * @param customerId 客戶ID
     * @return 總金額
     */
    public BigDecimal getTotalAmountByCustomer(Long customerId) {
        log.debug("計算客戶訂單總金額，客戶ID: {}", customerId);
        return orderRepository.sumAmountByCustomer(customerId);
    }

    /**
     * 根據訂單編號模糊查詢
     * @param orderNo 訂單編號關鍵字
     * @return 訂單列表
     */
    public List<Order> searchOrdersByNo(String orderNo) {
        log.debug("模糊查詢訂單編號: {}", orderNo);
        return orderRepository.findByNoContainingIgnoreCase(orderNo);
    }

    /**
     * 查詢有備註的訂單
     * @return 有備註的訂單列表
     */
    public List<Order> getOrdersWithNotes() {
        log.debug("查詢有備註的訂單");
        return orderRepository.findByNotesIsNotNull();
    }

    /**
     * 查詢沒有備註的訂單
     * @return 沒有備註的訂單列表
     */
    public List<Order> getOrdersWithoutNotes() {
        log.debug("查詢沒有備註的訂單");
        return orderRepository.findByNotesIsNull();
    }
}