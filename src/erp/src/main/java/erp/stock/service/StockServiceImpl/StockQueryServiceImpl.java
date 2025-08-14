package erp.stock.service.StockServiceImpl;

import erp.stock.dto.*;
import erp.stock.entity.Stock;
import erp.stock.mapper.StockMapper;
import erp.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 庫存查詢服務實作
 * 專門處理庫存相關的查詢操作
 */
@Component
@RequiredArgsConstructor
public class StockQueryServiceImpl {
    
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    /**
     * 根據庫存ID取得庫存資訊
     */
    public StockResponse getStockById(Long id) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("庫存記錄不存在: " + id));
        return stockMapper.toResponse(stock);
    }
    
    /**
     * 根據商品ID取得庫存資訊
     */
    public StockResponse getStockByProductId(Long productId) {
        Stock stock = stockRepository.findByProductId(productId)
            .orElse(null);
        return stock != null ? stockMapper.toResponse(stock) : null;
    }
    
    /**
     * 取得所有庫存（分頁）
     */
    public Page<StockResponse> getAllStocks(Pageable pageable) {
        Page<Stock> stocks = stockRepository.findAll(pageable);
        return stockMapper.toResponsePage(stocks);
    }
    
    /**
     * 搜尋庫存（分頁）
     */
    public Page<StockResponse> searchStocks(StockSearchRequest request, Pageable pageable) {
        Specification<Stock> spec = buildSearchSpecification(request);
        Page<Stock> stocks = stockRepository.findAll(spec, pageable);
        return stockMapper.toResponsePage(stocks);
    }
    
    /**
     * 根據商品ID清單取得庫存
     */
    public List<StockResponse> getStocksByProductIds(List<Long> productIds) {
        List<Stock> stocks = stockRepository.findByProductIdIn(productIds);
        return stockMapper.toResponseList(stocks);
    }
    
    /**
     * 取得低庫存商品
     */
    public List<StockResponse> getLowStockProducts(Integer threshold) {
        List<Stock> stocks = stockRepository.findByQtyLessThan(threshold);
        return stockMapper.toResponseList(stocks);
    }
    
    /**
     * 取得零庫存商品
     */
    public List<StockResponse> getZeroStockProducts() {
        return getLowStockProducts(1);
    }
    
    /**
     * 根據庫存價值範圍取得庫存
     */
    public List<StockResponse> getStocksByValueRange(BigDecimal minValue, BigDecimal maxValue) {
        List<Stock> stocks = stockRepository.findByTotalCostBetween(minValue, maxValue);
        return stockMapper.toResponseList(stocks);
    }
    
    // === 私有方法 ===
    
    /**
     * 建構搜尋條件
     */
    private Specification<Stock> buildSearchSpecification(StockSearchRequest request) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();
            
            if (request.getProductId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("productId"), request.getProductId()));
            }
            
            if (request.getMinQty() != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("qty"), request.getMinQty()));
            }
            
            if (request.getMaxQty() != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("qty"), request.getMaxQty()));
            }
            
            if (request.getMinAvgCost() != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("avgCost"), request.getMinAvgCost()));
            }
            
            if (request.getMaxAvgCost() != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("avgCost"), request.getMaxAvgCost()));
            }
            
            return predicates;
        };
    }
}