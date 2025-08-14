package erp.order.service.impl;

import erp.order.entity.OrderItem;
import erp.order.service.OrderItemService;
import erp.product.enums.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 訂單明細服務主實現類
 * 協調訂單明細查詢和創建更新功能模塊的實現
 */
@Service
@RequiredArgsConstructor
public class OrderItemServiceMainImpl implements OrderItemService {
    
    private final OrderItemQueryServiceImpl queryService;
    private final OrderItemCreateUpdateServiceImpl createUpdateService;
    
    //region 訂單明細查詢相關方法委派 (Read)
    @Override
    public OrderItem getOrderItemById(Long itemId) {
        return queryService.getOrderItemById(itemId);
    }
    
    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return queryService.getOrderItemsByOrderId(orderId);
    }
    
    @Override
    public Page<OrderItem> getAllOrderItems(Pageable pageable) {
        return queryService.getAllOrderItems(pageable);
    }
    
    @Override
    public List<OrderItem> getOrderItemsByProductId(Long productId) {
        return queryService.getOrderItemsByProductId(productId);
    }
    
    @Override
    public List<OrderItem> getOrderItemsByProductType(ProductType productType) {
        return queryService.getOrderItemsByProductType(productType);
    }
    
    @Override
    public List<OrderItem> getOrderItemsByQuantityRange(Integer minQty, Integer maxQty) {
        return queryService.getOrderItemsByQuantityRange(minQty, maxQty);
    }
    
    @Override
    public List<OrderItem> getOrderItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return queryService.getOrderItemsByPriceRange(minPrice, maxPrice);
    }
    //endregion
    
    //region 訂單明細操作相關方法委派 (Create & Update)
    @Override
    public OrderItem createOrderItem(Long orderId, Long productId, String productName, 
                                   ProductType productType, Integer qty, BigDecimal unitPrice, 
                                   String notes) {
        return createUpdateService.createOrderItem(orderId, productId, productName, productType, 
                                                 qty, unitPrice, notes);
    }
    
    @Override
    public OrderItem updateOrderItem(Long itemId, Long productId, String productName, 
                                   ProductType productType, Integer qty, BigDecimal unitPrice, 
                                   String notes) {
        return createUpdateService.updateOrderItem(itemId, productId, productName, productType, 
                                                 qty, unitPrice, notes);
    }
    
    @Override
    public void deleteOrderItem(Long itemId) {
        createUpdateService.deleteOrderItem(itemId);
    }
    //endregion
    
    //region 控制器專用業務邏輯區塊
    // 目前暫時為空，可在此添加特定的業務邏輯方法
    //endregion
}