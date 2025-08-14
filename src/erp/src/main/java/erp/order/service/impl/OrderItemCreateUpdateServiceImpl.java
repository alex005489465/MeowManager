package erp.order.service.impl;

import erp.order.entity.OrderItem;
import erp.order.repository.OrderItemRepository;
import erp.product.enums.ProductType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 訂單明細創建更新功能實現
 * 專門處理訂單明細相關的創建和更新業務邏輯
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderItemCreateUpdateServiceImpl {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemQueryServiceImpl queryService;

    /**
     * 創建訂單明細
     * @param orderId 訂單ID
     * @param productId 產品ID
     * @param productName 產品名稱
     * @param productType 產品類型
     * @param qty 數量
     * @param unitPrice 單價
     * @param notes 備註
     * @return 創建的訂單明細
     */
    public OrderItem createOrderItem(Long orderId, Long productId, String productName, 
                                    ProductType productType, Integer qty, BigDecimal unitPrice, 
                                    String notes) {
        log.info("創建訂單明細，訂單ID: {}, 產品ID: {}", orderId, productId);
        
        // 構建訂單明細實體
        OrderItem orderItem = OrderItem.builder()
                .orderId(orderId)
                .productId(productId)
                .productName(productName)
                .productType(productType)
                .qty(qty)
                .unitPrice(unitPrice)
                .notes(notes)
                .build();
        
        // 驗證明細數據
        validateOrderItem(orderItem);
        
        // 檢查是否已存在相同訂單和產品的明細
        validateUniqueOrderProduct(orderItem.getOrderId(), orderItem.getProductId(), null);
        
        // 保存明細
        OrderItem savedItem = orderItemRepository.save(orderItem);
        log.info("訂單明細創建成功，ID: {}, 訂單ID: {}, 產品: {}", 
                savedItem.getId(), savedItem.getOrderId(), savedItem.getProductName());
        return savedItem;
    }

    /**
     * 創建訂單明細（使用明細實體）
     * @param orderItem 訂單明細實體
     * @return 創建的訂單明細
     */
    public OrderItem createOrderItem(OrderItem orderItem) {
        log.info("創建訂單明細，訂單ID: {}, 產品ID: {}", orderItem.getOrderId(), orderItem.getProductId());
        
        // 設置預設值
        if (orderItem.getQty() == null) {
            orderItem.setQty(1);
        }
        
        if (orderItem.getUnitPrice() == null) {
            orderItem.setUnitPrice(BigDecimal.ZERO);
        }
        
        // 驗證明細數據
        validateOrderItem(orderItem);
        
        // 檢查是否已存在相同訂單和產品的明細
        validateUniqueOrderProduct(orderItem.getOrderId(), orderItem.getProductId(), null);
        
        // 保存明細
        OrderItem savedItem = orderItemRepository.save(orderItem);
        log.info("訂單明細創建成功，ID: {}, 訂單ID: {}, 產品: {}", 
                savedItem.getId(), savedItem.getOrderId(), savedItem.getProductName());
        return savedItem;
    }

    /**
     * 更新訂單明細
     * @param itemId 明細ID
     * @param productId 產品ID
     * @param productName 產品名稱
     * @param productType 產品類型
     * @param qty 數量
     * @param unitPrice 單價
     * @param notes 備註
     * @return 更新後的訂單明細
     */
    public OrderItem updateOrderItem(Long itemId, Long productId, String productName, 
                                    ProductType productType, Integer qty, BigDecimal unitPrice, 
                                    String notes) {
        log.info("更新訂單明細，ID: {}", itemId);
        
        // 獲取現有明細
        OrderItem existingItem = queryService.getOrderItemById(itemId);
        
        // 更新明細欄位
        updateOrderItemFields(existingItem, productId, productName, productType, qty, unitPrice, notes);
        
        // 驗證更新後的明細數據
        validateOrderItem(existingItem);
        
        // 如果產品ID有變更，檢查唯一性
        if (productId != null && !productId.equals(existingItem.getProductId())) {
            validateUniqueOrderProduct(existingItem.getOrderId(), productId, itemId);
        }
        
        // 保存更新
        OrderItem updatedItem = orderItemRepository.save(existingItem);
        log.info("訂單明細更新成功，ID: {}", updatedItem.getId());
        return updatedItem;
    }

    /**
     * 更新訂單明細（使用明細實體）
     * @param itemId 明細ID
     * @param orderItem 新的明細數據
     * @return 更新後的訂單明細
     */
    public OrderItem updateOrderItem(Long itemId, OrderItem orderItem) {
        log.info("更新訂單明細，ID: {}", itemId);
        
        // 獲取現有明細
        OrderItem existingItem = queryService.getOrderItemById(itemId);
        
        // 更新明細欄位
        updateOrderItemFields(existingItem, orderItem);
        
        // 驗證更新後的明細數據
        validateOrderItem(existingItem);
        
        // 如果產品ID有變更，檢查唯一性
        if (orderItem.getProductId() != null && !orderItem.getProductId().equals(existingItem.getProductId())) {
            validateUniqueOrderProduct(existingItem.getOrderId(), orderItem.getProductId(), itemId);
        }
        
        // 保存更新
        OrderItem updatedItem = orderItemRepository.save(existingItem);
        log.info("訂單明細更新成功，ID: {}", updatedItem.getId());
        return updatedItem;
    }

    /**
     * 批量創建訂單明細
     * @param orderId 訂單ID
     * @param orderItems 明細列表
     * @return 創建的明細列表
     */
    public java.util.List<OrderItem> createOrderItems(Long orderId, java.util.List<OrderItem> orderItems) {
        log.info("批量創建訂單明細，訂單ID: {}, 明細數量: {}", orderId, orderItems.size());
        
        // 設置訂單ID並驗證每個明細
        for (OrderItem item : orderItems) {
            item.setOrderId(orderId);
            validateOrderItem(item);
        }
        
        // 檢查產品重複
        validateNoDuplicateProducts(orderItems);
        
        // 批量保存
        java.util.List<OrderItem> savedItems = orderItemRepository.saveAll(orderItems);
        log.info("訂單明細批量創建成功，訂單ID: {}, 創建數量: {}", orderId, savedItems.size());
        return savedItems;
    }

    /**
     * 刪除指定訂單的所有明細
     * @param orderId 訂單ID
     */
    public void deleteOrderItemsByOrderId(Long orderId) {
        log.info("刪除訂單明細，訂單ID: {}", orderId);
        
        // 檢查訂單是否有明細
        if (queryService.hasOrderItems(orderId)) {
            orderItemRepository.deleteByOrderId(orderId);
            log.info("訂單明細刪除成功，訂單ID: {}", orderId);
        } else {
            log.info("訂單沒有明細，無需刪除，訂單ID: {}", orderId);
        }
    }

    /**
     * 刪除指定的訂單明細
     * @param itemId 明細ID
     */
    @Transactional
    public void deleteOrderItem(Long itemId) {
        log.info("刪除訂單明細，明細ID: {}", itemId);
        
        // 檢查明細是否存在
        OrderItem existingItem = queryService.getOrderItemById(itemId);
        if (existingItem != null) {
            orderItemRepository.deleteById(itemId);
            log.info("訂單明細刪除成功，明細ID: {}", itemId);
        } else {
            log.warn("訂單明細不存在，無需刪除，明細ID: {}", itemId);
        }
    }

    /**
     * 驗證訂單明細數據
     * @param orderItem 訂單明細實體
     */
    private void validateOrderItem(OrderItem orderItem) {
        // 驗證訂單ID
        if (orderItem.getOrderId() == null) {
            throw new IllegalArgumentException("訂單ID不能為空");
        }
        
        // 驗證產品ID
        if (orderItem.getProductId() == null) {
            throw new IllegalArgumentException("產品ID不能為空");
        }
        
        // 驗證產品名稱
        if (orderItem.getProductName() == null || orderItem.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("產品名稱不能為空");
        }
        
        // 驗證數量
        if (orderItem.getQty() == null || orderItem.getQty() <= 0) {
            throw new IllegalArgumentException("數量必須大於0");
        }
        
        // 驗證單價
        if (orderItem.getUnitPrice() == null || orderItem.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("單價不能為空且不能為負數");
        }
        
        // 驗證產品名稱長度
        if (orderItem.getProductName().length() > 255) {
            throw new IllegalArgumentException("產品名稱長度不能超過255個字符");
        }
        
        // 驗證備註長度
        if (orderItem.getNotes() != null && orderItem.getNotes().length() > 1000) {
            throw new IllegalArgumentException("備註長度不能超過1000個字符");
        }
    }

    /**
     * 驗證訂單產品唯一性
     * @param orderId 訂單ID
     * @param productId 產品ID
     * @param excludeItemId 要排除的明細ID（更新時使用）
     */
    private void validateUniqueOrderProduct(Long orderId, Long productId, Long excludeItemId) {
        boolean exists;
        if (excludeItemId == null) {
            // 創建時檢查
            exists = queryService.hasOrderItemByOrderAndProduct(orderId, productId);
        } else {
            // 更新時檢查（排除當前明細）
            OrderItem existingItem = queryService.getOrderItemByOrderAndProduct(orderId, productId);
            exists = existingItem != null && !existingItem.getId().equals(excludeItemId);
        }
        
        if (exists) {
            throw new IllegalArgumentException(String.format("訂單 %d 中已存在產品 %d 的明細", orderId, productId));
        }
    }

    /**
     * 驗證明細列表中沒有重複產品
     * @param orderItems 明細列表
     */
    private void validateNoDuplicateProducts(java.util.List<OrderItem> orderItems) {
        java.util.Set<Long> productIds = new java.util.HashSet<>();
        for (OrderItem item : orderItems) {
            if (productIds.contains(item.getProductId())) {
                throw new IllegalArgumentException("明細列表中存在重複的產品ID: " + item.getProductId());
            }
            productIds.add(item.getProductId());
        }
    }

    /**
     * 更新訂單明細欄位
     * @param existingItem 現有明細
     * @param productId 產品ID
     * @param productName 產品名稱
     * @param productType 產品類型
     * @param qty 數量
     * @param unitPrice 單價
     * @param notes 備註
     */
    private void updateOrderItemFields(OrderItem existingItem, Long productId, String productName,
                                      ProductType productType, Integer qty, BigDecimal unitPrice, 
                                      String notes) {
        if (productId != null) {
            existingItem.setProductId(productId);
        }
        if (productName != null) {
            existingItem.setProductName(productName);
        }
        if (productType != null) {
            existingItem.setProductType(productType);
        }
        if (qty != null) {
            existingItem.setQty(qty);
        }
        if (unitPrice != null) {
            existingItem.setUnitPrice(unitPrice);
        }
        if (notes != null) {
            existingItem.setNotes(notes);
        }
    }

    /**
     * 更新訂單明細欄位（使用明細實體）
     * @param existingItem 現有明細
     * @param newItem 新明細數據
     */
    private void updateOrderItemFields(OrderItem existingItem, OrderItem newItem) {
        updateOrderItemFields(existingItem,
                             newItem.getProductId(),
                             newItem.getProductName(),
                             newItem.getProductType(),
                             newItem.getQty(),
                             newItem.getUnitPrice(),
                             newItem.getNotes());
    }
}