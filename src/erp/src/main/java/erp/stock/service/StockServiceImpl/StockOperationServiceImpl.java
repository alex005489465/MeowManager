package erp.stock.service.StockServiceImpl;

import erp.stock.dto.StockResponse;
import erp.stock.entity.Stock;
import erp.stock.enums.MovementType;
import erp.stock.mapper.StockMapper;
import erp.stock.mapper.StockMovementMapper;
import erp.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 庫存操作服務實作
 * 專門處理庫存的建立、調整等操作
 */
@Component
@RequiredArgsConstructor
@Transactional
public class StockOperationServiceImpl {
    
    private final StockRepository stockRepository;
    private final StockMovementServiceImpl movementService;
    private final StockValidationServiceImpl validationService;
    private final StockMapper stockMapper;
    private final StockMovementMapper stockMovementMapper;
    
    /**
     * 為商品建立庫存記錄
     */
    public StockResponse createStock(Long productId) {
        // 驗證商品是否存在
        validationService.validateProductExists(productId);
        
        // 檢查是否已存在庫存記錄
        if (stockRepository.findByProductId(productId).isPresent()) {
            throw new RuntimeException("商品庫存記錄已存在: " + productId);
        }
        
        // 建立初始庫存記錄
        Stock stock = stockMapper.createEntity(productId);
        Stock savedStock = stockRepository.save(stock);
        return stockMapper.toResponse(savedStock);
    }
    
    /**
     * 調整庫存
     */
    public StockResponse adjustStock(Long productId, Integer quantity, BigDecimal unitCost, MovementType type) {
        return adjustStockWithReason(productId, quantity, unitCost, type, null);
    }
    
    /**
     * 調整庫存（帶原因）
     */
    public StockResponse adjustStockWithReason(Long productId, Integer quantity, BigDecimal unitCost, 
                                             MovementType type, String reason) {
        // 參數驗證
        validationService.validateStockAdjustment(productId, quantity, type);
        
        // 尋找或建立庫存記錄
        Stock stock = stockRepository.findByProductId(productId)
            .orElseGet(() -> createStockEntity(productId));
        
        // 執行庫存調整
        adjustStockQuantityAndCost(stock, quantity, unitCost, type);
        
        // 儲存庫存記錄
        Stock savedStock = stockRepository.save(stock);
        
        // 建立庫存變動記錄
        createMovementRecord(savedStock, quantity, unitCost, type, reason);
        
        return stockMapper.toResponse(savedStock);
    }
    
    /**
     * 刪除庫存記錄
     */
    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("庫存記錄不存在: " + id));
        
        // 檢查是否有庫存餘量
        if (stock.getQty() > 0) {
            throw new RuntimeException("有庫存餘量的記錄無法刪除，請先清空庫存");
        }
        
        stockRepository.deleteById(id);
    }
    
    //region 私有方法
    
    /**
     * 建立庫存實體
     */
    private Stock createStockEntity(Long productId) {
        validationService.validateProductExists(productId);
        return stockMapper.createEntity(productId);
    }
    
    /**
     * 調整庫存數量和成本
     */
    private void adjustStockQuantityAndCost(Stock stock, Integer quantity, BigDecimal unitCost, MovementType type) {
        Integer currentQty = stock.getQty();
        BigDecimal currentAvgCost = stock.getAvgCost();
        
        if (type == MovementType.IN) {
            // 入庫：增加庫存，重新計算平均成本
            Integer newQty = currentQty + quantity;
            BigDecimal newAvgCost = calculateWeightedAverageCost(
                currentQty, currentAvgCost, quantity, unitCost);
            
            stock.setQty(newQty);
            stock.setAvgCost(newAvgCost);
            stock.setTotalCost(newAvgCost.multiply(BigDecimal.valueOf(newQty)));
            
        } else if (type == MovementType.OUT) {
            // 出庫：驗證庫存充足性，減少庫存
            if (currentQty < quantity) {
                throw new RuntimeException("庫存不足，目前庫存: " + currentQty + "，需要出庫: " + quantity);
            }
            
            Integer newQty = currentQty - quantity;
            // 出庫不改變平均成本，只減少總價值
            stock.setQty(newQty);
            stock.setTotalCost(currentAvgCost.multiply(BigDecimal.valueOf(newQty)));
        }
    }
    
    /**
     * 計算加權平均成本
     */
    private BigDecimal calculateWeightedAverageCost(Integer currentQty, BigDecimal currentAvgCost, 
                                                  Integer inQty, BigDecimal inUnitCost) {
        if (currentQty == 0) {
            return inUnitCost;
        }
        
        BigDecimal currentTotal = currentAvgCost.multiply(BigDecimal.valueOf(currentQty));
        BigDecimal inTotal = inUnitCost.multiply(BigDecimal.valueOf(inQty));
        BigDecimal newTotal = currentTotal.add(inTotal);
        BigDecimal newQty = BigDecimal.valueOf(currentQty + inQty);
        
        return newTotal.divide(newQty, 2, RoundingMode.HALF_UP);
    }
    
    /**
     * 建立庫存變動記錄
     */
    private void createMovementRecord(Stock stock, Integer quantity, BigDecimal unitCost, 
                                    MovementType type, String reason) {
        // 使用movementService來保存記錄
        var createRequest = new erp.stock.dto.StockMovementCreateRequest();
        createRequest.setStockId(stock.getId());
        createRequest.setProductId(stock.getProductId());
        createRequest.setMovementType(type);
        createRequest.setQty(quantity);
        createRequest.setUnitCost(unitCost);
        // 注意：目前的StockMovementCreateRequest沒有reason欄位，可能需要後續擴展
        
        movementService.createStockMovement(createRequest);
    }
    //endregion
}