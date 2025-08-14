package erp.product.mapper;

import erp.product.dto.ProductCreateRequest;
import erp.product.dto.ProductUpdateRequest;
import erp.product.entity.Product;
import org.mapstruct.Mapper;

/**
 * 產品映射器
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    Product toEntity(ProductCreateRequest request);
    
    Product toEntity(ProductUpdateRequest request);
}