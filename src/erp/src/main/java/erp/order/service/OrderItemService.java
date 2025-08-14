package erp.order.service;

import erp.order.entity.OrderItem;
import erp.product.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 訂單明細服務介面
 * 專門處理訂單明細的業務操作
 */
public interface OrderItemService {
    
    //region 訂單明細查詢相關方法 (Read)
    OrderItem getOrderItemById(Long itemId);
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    Page<OrderItem> getAllOrderItems(Pageable pageable);
    List<OrderItem> getOrderItemsByProductId(Long productId);
    List<OrderItem> getOrderItemsByProductType(ProductType productType);
    List<OrderItem> getOrderItemsByQuantityRange(Integer minQty, Integer maxQty);
    List<OrderItem> getOrderItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    //endregion
    
    //region 訂單明細操作相關方法 (Create & Update)
    OrderItem createOrderItem(Long orderId, Long productId, String productName, 
                             ProductType productType, Integer qty, BigDecimal unitPrice, 
                             String notes);
    OrderItem updateOrderItem(Long itemId, Long productId, String productName, 
                             ProductType productType, Integer qty, BigDecimal unitPrice, 
                             String notes);
    void deleteOrderItem(Long itemId);
    //endregion
    
    //region 控制器專用業務邏輯區塊
    //暫時為空
    //endregion
}