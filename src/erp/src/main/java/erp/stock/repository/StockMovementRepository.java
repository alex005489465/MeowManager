package erp.stock.repository;

import erp.stock.entity.StockMovement;
import erp.stock.enums.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long>, JpaSpecificationExecutor<StockMovement> {
    
    /**
     * 根據庫存ID尋找變動記錄
     */
    List<StockMovement> findByStockIdOrderByCreatedAtDesc(Long stockId);
    
    /**
     * 根據商品ID尋找變動記錄
     */
    List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    /**
     * 根據日期範圍尋找變動記錄
     */
    List<StockMovement> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * 根據變動類型尋找記錄
     */
    List<StockMovement> findByMovementType(MovementType movementType);
}