package erp.stock.service.StockServiceImpl;

import erp.stock.enums.MovementType;
import erp.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 庫存驗證服務實作
 * 專門處理庫存相關的業務驗證
 */
@Component
@RequiredArgsConstructor
public class StockValidationServiceImpl {
    
    private final StockRepository stockRepository;
    // private final ProductService productService; // 假設存在產品服務
    
    /**
     * 驗證庫存調整參數
     */
    public void validateStockAdjustment(Long productId, Integer quantity, MovementType type) {
        if (productId == null) {
            throw new IllegalArgumentException("商品ID不能為空");
        }
        
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("調整數量必須大於0");
        }
        
        if (type == null) {
            throw new IllegalArgumentException("變動類型不能為空");
        }
        
        // 驗證商品存在性
        validateProductExists(productId);
        
        // 出庫時驗證庫存充足性
        if (type == MovementType.OUT) {
            validateStockSufficiency(productId, quantity);
        }
    }
    
    /**
     * 驗證商品是否存在
     */
    public void validateProductExists(Long productId) {
        // TODO: 呼叫產品服務驗證商品存在性
        // if (!productService.existsById(productId)) {
        //     throw new RuntimeException("商品不存在: " + productId);
        // }
    }
    
    /**
     * 驗證庫存充足性
     */
    public void validateStockSufficiency(Long productId, Integer quantity) {
        stockRepository.findByProductId(productId)
            .ifPresent(stock -> {
                if (stock.getQty() < quantity) {
                    throw new RuntimeException(
                        String.format("庫存不足，目前庫存: %d，需要出庫: %d", stock.getQty(), quantity)
                    );
                }
            });
    }
}