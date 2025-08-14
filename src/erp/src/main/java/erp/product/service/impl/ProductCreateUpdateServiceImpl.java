package erp.product.service.impl;

import erp.product.dto.ProductCreateRequest;
import erp.product.entity.Product;
import erp.product.enums.ProductStatus;
import erp.product.repository.ProductRepository;
import erp.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 產品創建更新服務實現
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductCreateUpdateServiceImpl {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    public Product createProduct(ProductCreateRequest request) {
        Product product = productMapper.toEntity(request);
        product.setStatus(ProductStatus.ACTIVE); // 預設為啟用狀態
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("產品不存在"));
        
        // 更新欄位
        existingProduct.setName(product.getName());
        existingProduct.setType(product.getType());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setNotes(product.getNotes());
        
        return productRepository.save(existingProduct);
    }
    
    public Product updateProductStatus(Long id, ProductStatus status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("產品不存在"));
        
        product.setStatus(status);
        return productRepository.save(product);
    }
}