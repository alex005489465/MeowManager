package erp.stock.service.StockServiceImpl;

import erp.stock.dto.*;
import erp.stock.entity.Stock;
import erp.stock.enums.MovementType;
import erp.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 庫存控制器專用服務實作
 * 專門處理控制器所需的業務邏輯方法
 */
@Component
@RequiredArgsConstructor
@Transactional
public class StockControllerServiceImpl {
    
    private final StockRepository stockRepository;
    private final StockOperationServiceImpl operationService;
    private final StockValidationServiceImpl validationService;
    private final StockMovementServiceImpl movementService;
    
    /**
     * 控制器專用：通過庫存異動進行入庫操作
     * 自動創建或更新庫存記錄
     */
    public StockMovementResponse processStockInbound(StockInboundRequest request) {
        // 參數驗證
        validateInboundRequest(request);
        
        // 執行庫存調整（入庫）
        operationService.adjustStockWithReason(
            request.getProductId(),
            request.getQty(),
            request.getUnitCost(),
            MovementType.IN,
            request.getReason()
        );
        
        // 創建庫存變動記錄
        StockMovementCreateRequest movementRequest = new StockMovementCreateRequest();
        movementRequest.setProductId(request.getProductId());
        movementRequest.setMovementType(MovementType.IN);
        movementRequest.setQty(request.getQty());
        movementRequest.setUnitCost(request.getUnitCost());
        
        // 獲取更新後的庫存記錄來設置stockId
        Stock stock = stockRepository.findByProductId(request.getProductId())
            .orElseThrow(() -> new RuntimeException("庫存記錄建立失敗"));
        movementRequest.setStockId(stock.getId());
        
        // 通過movement service創建記錄並返回
        return movementService.createStockMovement(movementRequest);
    }
    
    /**
     * 控制器專用：通過庫存異動進行出庫操作
     * 自動更新庫存記錄
     */
    public StockMovementResponse processStockOutbound(StockOutboundRequest request) {
        // 參數驗證
        validateOutboundRequest(request);
        
        // 檢查庫存是否充足
        StockAvailabilityResponse availability = checkStockAvailability(request.getProductId(), request.getQty());
        if (!availability.getIsAvailable()) {
            throw new RuntimeException("庫存不足，無法出庫。當前庫存: " + availability.getCurrentQty() + 
                                     "，需要數量: " + availability.getRequiredQty() + 
                                     "，缺貨數量: " + availability.getShortageQty());
        }
        
        // 獲取庫存記錄以取得平均成本
        Stock stock = stockRepository.findByProductId(request.getProductId())
            .orElseThrow(() -> new RuntimeException("庫存記錄不存在: " + request.getProductId()));
        
        // 執行庫存調整（出庫）- 使用當前庫存的平均成本
        operationService.adjustStockWithReason(
            request.getProductId(),
            request.getQty(),
            stock.getAvgCost(),
            MovementType.OUT,
            request.getReason()
        );
        
        // 創建庫存變動記錄
        StockMovementCreateRequest movementRequest = new StockMovementCreateRequest();
        movementRequest.setStockId(stock.getId());
        movementRequest.setProductId(request.getProductId());
        movementRequest.setMovementType(MovementType.OUT);
        movementRequest.setQty(request.getQty());
        movementRequest.setUnitCost(stock.getAvgCost());
        
        // 通過movement service創建記錄並返回
        return movementService.createStockMovement(movementRequest);
    }
    
    /**
     * 控制器專用：檢查庫存可用性
     * 用於出庫前的庫存檢查
     */
    public StockAvailabilityResponse checkStockAvailability(Long productId, Integer requiredQty) {
        // 參數驗證
        if (productId == null) {
            throw new IllegalArgumentException("商品ID不能為空");
        }
        if (requiredQty == null || requiredQty <= 0) {
            throw new IllegalArgumentException("所需數量必須大於0");
        }
        
        // 驗證商品是否存在
        validationService.validateProductExists(productId);
        
        // 查詢庫存記錄
        Stock stock = stockRepository.findByProductId(productId).orElse(null);
        
        Integer currentQty = (stock != null) ? stock.getQty() : 0;
        boolean isAvailable = currentQty >= requiredQty;
        Integer shortageQty = isAvailable ? 0 : (requiredQty - currentQty);
        
        return new StockAvailabilityResponse(
            productId,
            currentQty,
            requiredQty,
            isAvailable,
            shortageQty
        );
    }
    
    //region 私有驗證方法
    
    private void validateInboundRequest(StockInboundRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("入庫請求不能為空");
        }
        if (request.getProductId() == null) {
            throw new IllegalArgumentException("商品ID不能為空");
        }
        if (request.getQty() == null || request.getQty() <= 0) {
            throw new IllegalArgumentException("入庫數量必須大於0");
        }
        if (request.getUnitCost() == null || request.getUnitCost().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("單位成本不能為負數");
        }
    }
    
    private void validateOutboundRequest(StockOutboundRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("出庫請求不能為空");
        }
        if (request.getProductId() == null) {
            throw new IllegalArgumentException("商品ID不能為空");
        }
        if (request.getQty() == null || request.getQty() <= 0) {
            throw new IllegalArgumentException("出庫數量必須大於0");
        }
    }
    //endregion
}