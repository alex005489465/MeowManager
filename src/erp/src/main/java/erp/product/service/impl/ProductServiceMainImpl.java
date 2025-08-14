package erp.product.service.impl;

import erp.product.dto.ProductCreateRequest;
import erp.product.dto.ProductSearchRequest;
import erp.product.dto.ProductStatistics;
import erp.product.entity.Product;
import erp.product.enums.ProductStatus;
import erp.product.enums.ProductType;
import erp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 產品服務主實現類
 * 統一協調產品相關的所有業務操作
 */
@Service
@RequiredArgsConstructor
public class ProductServiceMainImpl implements ProductService {
    
    private final ProductQueryServiceImpl queryService;
    private final ProductCreateUpdateServiceImpl createUpdateService;
    
    //region 查詢方法委派
    @Override
    public Product getProductById(Long id) {
        return queryService.getProductById(id);
    }
    
    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return queryService.getAllProducts(pageable);
    }
    
    @Override
    public List<Product> getProductsByType(ProductType type) {
        return queryService.getProductsByType(type);
    }
    
    @Override
    public List<Product> getProductsByStatus(ProductStatus status) {
        return queryService.getProductsByStatus(status);
    }
    
    @Override
    public List<Product> searchProductsByName(String name) {
        return queryService.searchProductsByName(name);
    }
    
    @Override
    public Page<Product> searchProducts(ProductSearchRequest request, Pageable pageable) {
        return queryService.searchProducts(request, pageable);
    }
    
    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return queryService.getProductsByPriceRange(minPrice, maxPrice);
    }
    
    @Override
    public ProductStatistics getProductStatistics() {
        return queryService.getProductStatistics();
    }
    
    //endregion
    //region 創建更新方法委派
    @Override
    public Product createProduct(ProductCreateRequest request) {
        return createUpdateService.createProduct(request);
    }
    
    @Override
    public Product updateProduct(Long id, Product product) {
        return createUpdateService.updateProduct(id, product);
    }
    
    @Override
    public Product updateProductStatus(Long id, ProductStatus status) {
        return createUpdateService.updateProductStatus(id, status);
    }

    //endregion
}
