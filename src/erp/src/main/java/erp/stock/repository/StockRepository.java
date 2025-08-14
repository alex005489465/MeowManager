package erp.stock.repository;

import erp.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {
    
    /**
     * 根據商品ID尋找庫存
     */
    Optional<Stock> findByProductId(Long productId);
    
    /**
     * 根據商品ID清單尋找庫存
     */
    List<Stock> findByProductIdIn(List<Long> productIds);
    
    /**
     * 尋找低於指定數量的庫存
     */
    List<Stock> findByQtyLessThan(Integer threshold);
    
    /**
     * 根據庫存價值範圍尋找
     */
    List<Stock> findByTotalCostBetween(BigDecimal minValue, BigDecimal maxValue);
}