package erp.stock.service.StockServiceImpl;

import erp.stock.dto.*;
import erp.stock.entity.StockMovement;
import erp.stock.enums.MovementType;
import erp.stock.mapper.StockMovementMapper;
import erp.stock.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 庫存變動記錄服務實作
 * 專門處理庫存變動記錄的管理
 */
@Component
@RequiredArgsConstructor
public class StockMovementServiceImpl {
    
    private final StockMovementRepository stockMovementRepository;
    private final StockMovementMapper stockMovementMapper;
    
    /**
     * 取得庫存變動記錄（分頁搜尋）
     */
    public Page<StockMovementResponse> getStockMovements(StockMovementSearchRequest request, Pageable pageable) {
        Specification<StockMovement> spec = buildMovementSearchSpecification(request);
        Page<StockMovement> movements = stockMovementRepository.findAll(spec, pageable);
        return stockMovementMapper.toResponsePage(movements);
    }
    
    /**
     * 建立庫存變動記錄
     */
    public StockMovementResponse createStockMovement(StockMovementCreateRequest request) {
        StockMovement movement = stockMovementMapper.toEntity(request);
        StockMovement savedMovement = stockMovementRepository.save(movement);
        return stockMovementMapper.toResponse(savedMovement);
    }
    
    /**
     * 根據商品ID取得變動記錄
     */
    public List<StockMovementResponse> getMovementsByProductId(Long productId) {
        List<StockMovement> movements = stockMovementRepository
            .findByProductIdOrderByCreatedAtDesc(productId);
        return stockMovementMapper.toResponseList(movements);
    }
    
    /**
     * 根據庫存ID取得變動記錄
     */
    public List<StockMovementResponse> getMovementsByStockId(Long stockId) {
        List<StockMovement> movements = stockMovementRepository
            .findByStockIdOrderByCreatedAtDesc(stockId);
        return stockMovementMapper.toResponseList(movements);
    }
    
    /**
     * 根據日期範圍取得變動記錄
     */
    public List<StockMovementResponse> getMovementsByDateRange(LocalDateTime start, LocalDateTime end) {
        List<StockMovement> movements = stockMovementRepository
            .findByCreatedAtBetween(start, end);
        return stockMovementMapper.toResponseList(movements);
    }
    
    /**
     * 根據變動類型取得記錄
     */
    public List<StockMovementResponse> getMovementsByType(MovementType movementType) {
        List<StockMovement> movements = stockMovementRepository
            .findByMovementType(movementType);
        return stockMovementMapper.toResponseList(movements);
    }
    
    // === 私有方法 ===
    
    /**
     * 建構變動記錄搜尋條件
     */
    private Specification<StockMovement> buildMovementSearchSpecification(StockMovementSearchRequest request) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();
            
            if (request.getStockId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("stockId"), request.getStockId()));
            }
            
            if (request.getProductId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("productId"), request.getProductId()));
            }
            
            if (request.getMovementType() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("movementType"), request.getMovementType()));
            }
            
            if (request.getStartTime() != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("createdAt"), request.getStartTime()));
            }
            
            if (request.getEndTime() != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("createdAt"), request.getEndTime()));
            }
            
            if (request.getMinQty() != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("qty"), request.getMinQty()));
            }
            
            if (request.getMaxQty() != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("qty"), request.getMaxQty()));
            }
            
            return predicates;
        };
    }
}