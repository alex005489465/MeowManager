package erp.product.service;

import erp.product.dto.*;
import erp.product.entity.Product;
import erp.product.enums.ProductStatus;
import erp.product.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 產品服務統一接口
 * 整合所有產品相關的業務操作
 */
public interface ProductService {
    
    //region 查詢相關方法
    Product getProductById(Long id);
    Page<Product> getAllProducts(Pageable pageable);
    List<Product> getProductsByType(ProductType type);
    List<Product> getProductsByStatus(ProductStatus status);
    List<Product> searchProductsByName(String name);
    Page<Product> searchProducts(ProductSearchRequest request, Pageable pageable);
    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    ProductStatistics getProductStatistics();
    
    //endregion
    //region 創建更新相關方法
    Product createProduct(ProductCreateRequest request);
    Product updateProduct(Long id, Product product);
    Product updateProductStatus(Long id, ProductStatus status);

    //endregion
}
