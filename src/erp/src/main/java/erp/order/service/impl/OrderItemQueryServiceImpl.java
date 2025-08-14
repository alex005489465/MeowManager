package erp.order.service.impl;

import erp.common.constant.ErrorCode;
import erp.common.exception.OrderException;
import erp.order.constant.OrderConstants;
import erp.order.entity.OrderItem;
import erp.order.repository.OrderItemRepository;
import erp.product.enums.ProductType;
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
 * 訂單明細查詢功能實現
 * 專門處理訂單明細相關的查詢業務邏輯
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderItemQueryServiceImpl {

    private final OrderItemRepository orderItemRepository;

    /**
     * 根據ID查找訂單明細
     * @param itemId 明細ID
     * @return 訂單明細實體
     * @throws OrderException 當明細不存在時
     */
    public OrderItem getOrderItemById(Long itemId) {
        log.debug("查詢訂單明細，ID: {}", itemId);
        return orderItemRepository.findById(itemId)
                .orElseThrow(() -> new OrderException("訂單明細不存在，ID: " + itemId, ErrorCode.ORDER_ITEM_NOT_FOUND));
    }

    /**
     * 根據訂單ID查找訂單明細
     * @param orderId 訂單ID
     * @return 訂單明細列表
     */
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        log.debug("查詢訂單明細，訂單ID: {}", orderId);
        return orderItemRepository.findByOrderId(orderId);
    }

    /**
     * 獲取所有訂單明細（分頁）
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    public Page<OrderItem> getAllOrderItems(Pageable pageable) {
        log.debug("查詢所有訂單明細，分頁參數: {}", pageable);
        return orderItemRepository.findAll(pageable);
    }

    /**
     * 根據產品ID查找訂單明細
     * @param productId 產品ID
     * @return 訂單明細列表
     */
    public List<OrderItem> getOrderItemsByProductId(Long productId) {
        log.debug("查詢訂單明細，產品ID: {}", productId);
        return orderItemRepository.findByProductId(productId);
    }

    /**
     * 根據產品類型查找訂單明細
     * @param productType 產品類型
     * @return 訂單明細列表
     */
    public List<OrderItem> getOrderItemsByProductType(ProductType productType) {
        log.debug("查詢訂單明細，產品類型: {}", productType);
        return orderItemRepository.findByProductType(productType);
    }

    /**
     * 根據數量範圍查找訂單明細
     * @param minQty 最小數量
     * @param maxQty 最大數量
     * @return 訂單明細列表
     */
    public List<OrderItem> getOrderItemsByQuantityRange(Integer minQty, Integer maxQty) {
        log.debug("查詢訂單明細，數量範圍: {} - {}", minQty, maxQty);
        return orderItemRepository.findByQtyBetween(minQty, maxQty);
    }

    /**
     * 根據單價範圍查找訂單明細
     * @param minPrice 最小單價
     * @param maxPrice 最大單價
     * @return 訂單明細列表
     */
    public List<OrderItem> getOrderItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.debug("查詢訂單明細，單價範圍: {} - {}", minPrice, maxPrice);
        return orderItemRepository.findByUnitPriceBetween(minPrice, maxPrice);
    }

    /**
     * 根據訂單ID分頁查找訂單明細
     * @param orderId 訂單ID
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    public Page<OrderItem> getOrderItemsByOrderId(Long orderId, Pageable pageable) {
        log.debug("分頁查詢訂單明細，訂單ID: {}, 分頁參數: {}", orderId, pageable);
        return orderItemRepository.findByOrderId(orderId, pageable);
    }

    /**
     * 根據產品ID分頁查找訂單明細
     * @param productId 產品ID
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    public Page<OrderItem> getOrderItemsByProductId(Long productId, Pageable pageable) {
        log.debug("分頁查詢訂單明細，產品ID: {}, 分頁參數: {}", productId, pageable);
        return orderItemRepository.findByProductId(productId, pageable);
    }

    /**
     * 根據產品類型分頁查找訂單明細
     * @param productType 產品類型
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    public Page<OrderItem> getOrderItemsByProductType(ProductType productType, Pageable pageable) {
        log.debug("分頁查詢訂單明細，產品類型: {}, 分頁參數: {}", productType, pageable);
        return orderItemRepository.findByProductType(productType, pageable);
    }

    /**
     * 根據數量範圍分頁查找訂單明細
     * @param minQty 最小數量
     * @param maxQty 最大數量
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    public Page<OrderItem> getOrderItemsByQuantityRange(Integer minQty, Integer maxQty, Pageable pageable) {
        log.debug("分頁查詢訂單明細，數量範圍: {} - {}, 分頁參數: {}", minQty, maxQty, pageable);
        return orderItemRepository.findByQtyBetween(minQty, maxQty, pageable);
    }

    /**
     * 根據單價範圍分頁查找訂單明細
     * @param minPrice 最小單價
     * @param maxPrice 最大單價
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    public Page<OrderItem> getOrderItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        log.debug("分頁查詢訂單明細，單價範圍: {} - {}, 分頁參數: {}", minPrice, maxPrice, pageable);
        return orderItemRepository.findByUnitPriceBetween(minPrice, maxPrice, pageable);
    }

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
    public Page<OrderItem> searchOrderItems(Long orderId, Long productId, ProductType productType,
                                          Integer minQty, Integer maxQty, 
                                          BigDecimal minPrice, BigDecimal maxPrice,
                                          String productName, Pageable pageable) {
        log.debug("多條件查詢訂單明細 - 訂單ID: {}, 產品ID: {}, 產品類型: {}, 數量範圍: {}-{}, 單價範圍: {}-{}, 產品名稱: {}", 
                orderId, productId, productType, minQty, maxQty, minPrice, maxPrice, productName);
        return orderItemRepository.findByMultipleConditions(orderId, productId, productType, 
                                                           minQty, maxQty, minPrice, maxPrice, 
                                                           productName, pageable);
    }

    /**
     * 檢查指定訂單是否有明細
     * @param orderId 訂單ID
     * @return 是否存在明細
     */
    public boolean hasOrderItems(Long orderId) {
        log.debug("檢查訂單是否有明細，訂單ID: {}", orderId);
        return orderItemRepository.existsByOrderId(orderId);
    }

    /**
     * 統計指定訂單的明細數量
     * @param orderId 訂單ID
     * @return 明細數量
     */
    public long countOrderItems(Long orderId) {
        log.debug("統計訂單明細數量，訂單ID: {}", orderId);
        return orderItemRepository.countByOrderId(orderId);
    }

    /**
     * 檢查產品是否在任何訂單中被使用
     * @param productId 產品ID
     * @return 是否被使用
     */
    public boolean isProductInUse(Long productId) {
        log.debug("檢查產品是否被使用，產品ID: {}", productId);
        return orderItemRepository.existsByProductId(productId);
    }

    /**
     * 根據產品名稱查詢訂單明細
     * @param productName 產品名稱
     * @return 訂單明細列表
     */
    public List<OrderItem> getOrderItemsByProductName(String productName) {
        log.debug("查詢訂單明細，產品名稱: {}", productName);
        return orderItemRepository.findByProductName(productName);
    }

    /**
     * 根據產品名稱模糊查詢訂單明細
     * @param productName 產品名稱關鍵字
     * @return 訂單明細列表
     */
    public List<OrderItem> searchOrderItemsByProductName(String productName) {
        log.debug("模糊查詢訂單明細，產品名稱: {}", productName);
        return orderItemRepository.findByProductNameContainingIgnoreCase(productName);
    }

    /**
     * 根據明細總額範圍查詢（單價 × 數量）
     * @param minAmount 最小總額
     * @param maxAmount 最大總額
     * @return 訂單明細列表
     */
    public List<OrderItem> getOrderItemsByTotalAmount(BigDecimal minAmount, BigDecimal maxAmount) {
        log.debug("查詢訂單明細，總額範圍: {} - {}", minAmount, maxAmount);
        return orderItemRepository.findByTotalAmountBetween(minAmount, maxAmount);
    }

    /**
     * 根據明細總額範圍分頁查詢（單價 × 數量）
     * @param minAmount 最小總額
     * @param maxAmount 最大總額
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    public Page<OrderItem> getOrderItemsByTotalAmount(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable) {
        log.debug("分頁查詢訂單明細，總額範圍: {} - {}, 分頁參數: {}", minAmount, maxAmount, pageable);
        return orderItemRepository.findByTotalAmountBetween(minAmount, maxAmount, pageable);
    }

    /**
     * 獲取最新的訂單明細
     * @return 最新10個訂單明細
     */
    public List<OrderItem> getRecentOrderItems() {
        log.debug("查詢最新訂單明細");
        return orderItemRepository.findTop10ByOrderByCreatedAtDesc();
    }

    /**
     * 獲取單價最高的訂單明細
     * @return 單價最高的10個明細
     */
    public List<OrderItem> getHighestPriceOrderItems() {
        log.debug("查詢單價最高訂單明細");
        return orderItemRepository.findTop10ByOrderByUnitPriceDesc();
    }

    /**
     * 獲取數量最多的訂單明細
     * @return 數量最多的10個明細
     */
    public List<OrderItem> getHighestQuantityOrderItems() {
        log.debug("查詢數量最多訂單明細");
        return orderItemRepository.findTop10ByOrderByQtyDesc();
    }

    /**
     * 根據創建日期範圍查詢訂單明細
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @return 訂單明細列表
     */
    public List<OrderItem> getOrderItemsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("查詢訂單明細，日期範圍: {} - {}", startDate, endDate);
        return orderItemRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * 根據創建日期範圍分頁查詢訂單明細
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @param pageable 分頁參數
     * @return 訂單明細分頁結果
     */
    public Page<OrderItem> getOrderItemsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.debug("分頁查詢訂單明細，日期範圍: {} - {}, 分頁參數: {}", startDate, endDate, pageable);
        return orderItemRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    /**
     * 統計各產品類型的明細數量
     * @return 產品類型-數量對應列表
     */
    public List<Object[]> getProductTypeStatistics() {
        log.debug("統計產品類型分佈");
        return orderItemRepository.countByProductType();
    }

    /**
     * 統計各產品的明細數量
     * @return 產品ID-數量對應列表
     */
    public List<Object[]> getProductStatistics() {
        log.debug("統計產品明細分佈");
        return orderItemRepository.countByProduct();
    }

    /**
     * 統計各產品的總銷售數量
     * @return 產品ID-總數量對應列表
     */
    public List<Object[]> getProductQuantityStatistics() {
        log.debug("統計產品總銷售數量");
        return orderItemRepository.sumQuantityByProduct();
    }

    /**
     * 統計各產品的總銷售金額
     * @return 產品ID-總金額對應列表
     */
    public List<Object[]> getProductAmountStatistics() {
        log.debug("統計產品總銷售金額");
        return orderItemRepository.sumAmountByProduct();
    }

    /**
     * 統計各產品類型的平均單價
     * @return 產品類型-平均單價對應列表
     */
    public List<Object[]> getProductTypeAvgPriceStatistics() {
        log.debug("統計產品類型平均單價");
        return orderItemRepository.avgPriceByProductType();
    }

    /**
     * 計算指定訂單的總金額
     * @param orderId 訂單ID
     * @return 總金額
     */
    public BigDecimal getTotalAmountByOrder(Long orderId) {
        log.debug("計算訂單總金額，訂單ID: {}", orderId);
        return orderItemRepository.sumAmountByOrder(orderId);
    }

    /**
     * 計算指定產品的總銷售數量
     * @param productId 產品ID
     * @return 總銷售數量
     */
    public Long getTotalQuantityByProduct(Long productId) {
        log.debug("計算產品總銷售數量，產品ID: {}", productId);
        return orderItemRepository.sumQuantityByProductId(productId);
    }

    /**
     * 計算指定產品類型的平均單價
     * @param productType 產品類型
     * @return 平均單價
     */
    public BigDecimal getAveragePriceByProductType(ProductType productType) {
        log.debug("計算產品類型平均單價，產品類型: {}", productType);
        return orderItemRepository.avgPriceByProductType(productType);
    }

    /**
     * 查詢有備註的訂單明細
     * @return 有備註的訂單明細列表
     */
    public List<OrderItem> getOrderItemsWithNotes() {
        log.debug("查詢有備註的訂單明細");
        return orderItemRepository.findByNotesIsNotNull();
    }

    /**
     * 查詢沒有備註的訂單明細
     * @return 沒有備註的訂單明細列表
     */
    public List<OrderItem> getOrderItemsWithoutNotes() {
        log.debug("查詢沒有備註的訂單明細");
        return orderItemRepository.findByNotesIsNull();
    }

    /**
     * 根據備註內容模糊查詢
     * @param notes 備註關鍵字
     * @return 訂單明細列表
     */
    public List<OrderItem> searchOrderItemsByNotes(String notes) {
        log.debug("模糊查詢訂單明細備註: {}", notes);
        return orderItemRepository.findByNotesContainingIgnoreCase(notes);
    }

    /**
     * 查詢指定訂單和產品的明細
     * @param orderId 訂單ID
     * @param productId 產品ID
     * @return 訂單明細（可能為空）
     */
    public OrderItem getOrderItemByOrderAndProduct(Long orderId, Long productId) {
        log.debug("查詢訂單產品明細，訂單ID: {}, 產品ID: {}", orderId, productId);
        return orderItemRepository.findByOrderIdAndProductId(orderId, productId)
                .orElse(null);
    }

    /**
     * 檢查指定訂單和產品是否已有明細
     * @param orderId 訂單ID
     * @param productId 產品ID
     * @return 是否存在明細
     */
    public boolean hasOrderItemByOrderAndProduct(Long orderId, Long productId) {
        log.debug("檢查訂單產品明細是否存在，訂單ID: {}, 產品ID: {}", orderId, productId);
        return orderItemRepository.existsByOrderIdAndProductId(orderId, productId);
    }
}