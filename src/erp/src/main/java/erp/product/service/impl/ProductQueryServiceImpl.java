package erp.product.service.impl;

import erp.product.dto.ProductSearchRequest;
import erp.product.dto.ProductStatistics;
import erp.product.entity.Product;
import erp.product.enums.ProductStatus;
import erp.product.enums.ProductType;
import erp.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 產品查詢服務實現
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductQueryServiceImpl {
    
    private final ProductRepository productRepository;
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("產品不存在"));
    }
    
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    
    public List<Product> getProductsByType(ProductType type) {
        return productRepository.findByType(type);
    }
    
    public List<Product> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status);
    }
    
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    public Page<Product> searchProducts(ProductSearchRequest request, Pageable pageable) {
        // 實現多條件搜索邏輯
        return productRepository.findByMultipleConditions(
                request.getName(),
                request.getType(),
                request.getStatus(),
                request.getMinPrice(),
                request.getMaxPrice(),
                request.getDescription(),
                pageable
        );
    }
    
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public ProductStatistics getProductStatistics() {
        // 實現統計邏輯
        Long total = productRepository.count();
        
        Map<String, Long> statusStats = new HashMap<>();
        List<Object[]> statusResults = productRepository.countByStatus();
        for (Object[] result : statusResults) {
            statusStats.put(result[0].toString(), (Long) result[1]);
        }
        
        Map<String, Long> typeStats = new HashMap<>();
        List<Object[]> typeResults = productRepository.countByType();
        for (Object[] result : typeResults) {
            typeStats.put(result[0].toString(), (Long) result[1]);
        }
        
        Long activeCount = (long) productRepository.findByStatus(ProductStatus.ACTIVE).size();
        Long disabledCount = (long) productRepository.findByStatus(ProductStatus.DISABLED).size();
        
        return new ProductStatistics(
                total,
                statusStats,
                typeStats,
                activeCount,
                disabledCount
        );
    }
}