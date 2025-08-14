package erp.stock.service;

import erp.stock.dto.*;
import erp.stock.enums.MovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 庫存服務統一介面
 * 整合所有庫存相關的業務操作
 */
public interface StockService {
    
    //region 庫存查詢相關方法
    StockResponse getStockById(Long id);
    StockResponse getStockByProductId(Long productId);
    Page<StockResponse> getAllStocks(Pageable pageable);
    Page<StockResponse> searchStocks(StockSearchRequest request, Pageable pageable);
    List<StockResponse> getStocksByProductIds(List<Long> productIds);
    List<StockResponse> getLowStockProducts(Integer threshold);
    List<StockResponse> getZeroStockProducts();
    List<StockResponse> getStocksByValueRange(BigDecimal minValue, BigDecimal maxValue);
    //endregion
    
    //region 庫存操作相關方法
    StockResponse createStock(Long productId);
    StockResponse adjustStock(Long productId, Integer quantity, BigDecimal unitCost, MovementType type);
    StockResponse adjustStockWithReason(Long productId, Integer quantity, BigDecimal unitCost, MovementType type, String reason);
    void deleteStock(Long id);
    //endregion
    
    //region 庫存變動記錄相關方法
    Page<StockMovementResponse> getStockMovements(StockMovementSearchRequest request, Pageable pageable);
    StockMovementResponse createStockMovement(StockMovementCreateRequest request);
    List<StockMovementResponse> getMovementsByProductId(Long productId);
    List<StockMovementResponse> getMovementsByStockId(Long stockId);
    List<StockMovementResponse> getMovementsByDateRange(LocalDateTime start, LocalDateTime end);
    List<StockMovementResponse> getMovementsByType(MovementType movementType);
    //endregion
    
    //region 控制器專用業務邏輯區塊
    /**
     * 控制器專用：通過庫存異動進行入庫操作
     * 自動創建或更新庫存記錄
     */
    StockMovementResponse processStockInbound(StockInboundRequest request);
    
    /**
     * 控制器專用：通過庫存異動進行出庫操作
     * 自動更新庫存記錄
     */
    StockMovementResponse processStockOutbound(StockOutboundRequest request);

    /**
     * 控制器專用：檢查庫存可用性
     * 用於出庫前的庫存檢查
     */
    StockAvailabilityResponse checkStockAvailability(Long productId, Integer requiredQty);
    //endregion
}