package erp.stock.service.StockServiceImpl;

import erp.stock.dto.*;
import erp.stock.enums.MovementType;
import erp.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 庫存服務主實作類
 * 統一協調庫存相關的所有業務操作
 */
@Service
@RequiredArgsConstructor
public class StockServiceMainImpl implements StockService {
    
    private final StockQueryServiceImpl queryService;
    private final StockOperationServiceImpl operationService;
    private final StockMovementServiceImpl movementService;
    private final StockControllerServiceImpl controllerService;
    
    //region 查詢方法委派
    @Override
    public StockResponse getStockById(Long id) {
        return queryService.getStockById(id);
    }
    
    @Override
    public StockResponse getStockByProductId(Long productId) {
        return queryService.getStockByProductId(productId);
    }
    
    @Override
    public Page<StockResponse> getAllStocks(Pageable pageable) {
        return queryService.getAllStocks(pageable);
    }
    
    @Override
    public Page<StockResponse> searchStocks(StockSearchRequest request, Pageable pageable) {
        return queryService.searchStocks(request, pageable);
    }
    
    @Override
    public List<StockResponse> getStocksByProductIds(List<Long> productIds) {
        return queryService.getStocksByProductIds(productIds);
    }
    
    @Override
    public List<StockResponse> getLowStockProducts(Integer threshold) {
        return queryService.getLowStockProducts(threshold);
    }
    
    @Override
    public List<StockResponse> getZeroStockProducts() {
        return queryService.getZeroStockProducts();
    }
    
    @Override
    public List<StockResponse> getStocksByValueRange(BigDecimal minValue, BigDecimal maxValue) {
        return queryService.getStocksByValueRange(minValue, maxValue);
    }
    //endregion
    
    //region 庫存操作方法委派
    @Override
    public StockResponse createStock(Long productId) {
        return operationService.createStock(productId);
    }
    
    @Override
    public StockResponse adjustStock(Long productId, Integer quantity, BigDecimal unitCost, MovementType type) {
        return operationService.adjustStock(productId, quantity, unitCost, type);
    }
    
    @Override
    public StockResponse adjustStockWithReason(Long productId, Integer quantity, BigDecimal unitCost, 
                                             MovementType type, String reason) {
        return operationService.adjustStockWithReason(productId, quantity, unitCost, type, reason);
    }
    
    @Override
    public void deleteStock(Long id) {
        operationService.deleteStock(id);
    }
    //endregion
    
    //region 庫存變動記錄方法委派
    @Override
    public Page<StockMovementResponse> getStockMovements(StockMovementSearchRequest request, Pageable pageable) {
        return movementService.getStockMovements(request, pageable);
    }
    
    @Override
    public StockMovementResponse createStockMovement(StockMovementCreateRequest request) {
        return movementService.createStockMovement(request);
    }
    
    @Override
    public List<StockMovementResponse> getMovementsByProductId(Long productId) {
        return movementService.getMovementsByProductId(productId);
    }
    
    @Override
    public List<StockMovementResponse> getMovementsByStockId(Long stockId) {
        return movementService.getMovementsByStockId(stockId);
    }
    
    @Override
    public List<StockMovementResponse> getMovementsByDateRange(LocalDateTime start, LocalDateTime end) {
        return movementService.getMovementsByDateRange(start, end);
    }
    
    @Override
    public List<StockMovementResponse> getMovementsByType(MovementType movementType) {
        return movementService.getMovementsByType(movementType);
    }
    //endregion
    
    //region 控制器專用業務邏輯方法委派
    @Override
    public StockMovementResponse processStockInbound(StockInboundRequest request) {
        return controllerService.processStockInbound(request);
    }
    
    @Override
    public StockMovementResponse processStockOutbound(StockOutboundRequest request) {
        return controllerService.processStockOutbound(request);
    }
    
    @Override
    public StockAvailabilityResponse checkStockAvailability(Long productId, Integer requiredQty) {
        return controllerService.checkStockAvailability(productId, requiredQty);
    }
    //endregion
}