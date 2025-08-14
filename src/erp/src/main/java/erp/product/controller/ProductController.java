package erp.product.controller;

import erp.common.controller.BaseController;
import erp.common.dto.ApiResponse;
import erp.common.dto.BasePageResponse;
import erp.common.dto.BasePageableRequest;
import erp.product.constant.ProductConstants;
import erp.product.constant.ProductApiConstants;
import erp.product.entity.Product;
import erp.product.service.ProductService;
import erp.product.mapper.ProductMapper;
import erp.product.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import erp.common.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 產品控制器
 */
@RestController
@RequestMapping(ProductApiConstants.API_BASE_PATH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "產品管理", description = "產品相關的CRUD操作和查詢功能")
public class ProductController extends BaseController<Product, Long> {

    private final ProductService productService;
    private final ProductMapper productMapper;

    //region 基本CRUD操作
    @Operation(summary = "創建產品", description = "創建新的產品資料")
    @StandardCreateResponse
    @PostMapping(ProductApiConstants.CREATE_PATH)
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        logRequest("創建產品", request.getName());
        
        Product createdProduct = productService.createProduct(request);
        
        logResponse("創建產品", createdProduct.getId());
        return created(ProductConstants.PRODUCT_CREATE_SUCCESS, createdProduct);
    }

    @Operation(summary = "更新產品", description = "更新現有產品的資料")
    @StandardUpdateResponse
    @PostMapping(ProductApiConstants.UPDATE_PATH)
    public ResponseEntity<ApiResponse<Product>> updateProduct(@Valid @RequestBody ProductUpdateRequest request) {
        logRequest("更新產品", request.getId());
        
        Product product = productMapper.toEntity(request);
        Product updatedProduct = productService.updateProduct(request.getId(), product);
        
        logResponse("更新產品", updatedProduct.getId());
        return success(ProductConstants.PRODUCT_UPDATE_SUCCESS, updatedProduct);
    }

    @Operation(summary = "更新產品狀態", description = "更新產品的啟用/停用狀態")
    @StandardUpdateResponse
    @PostMapping(ProductApiConstants.UPDATE_STATUS_PATH)
    public ResponseEntity<ApiResponse<Product>> updateProductStatus(@Valid @RequestBody ProductStatusUpdateRequest request) {
        logRequest("更新產品狀態", request.getId(), request.getStatus());
        
        Product updatedProduct = productService.updateProductStatus(request.getId(), request.getStatus());
        
        logResponse("更新產品狀態", updatedProduct.getId());
        return success(ProductConstants.PRODUCT_STATUS_UPDATE_SUCCESS, updatedProduct);
    }
    //endregion

    //region 搜索查詢操作
    @Operation(summary = "根據ID獲取產品", description = "通過產品ID查詢單個產品資料")
    @StandardResourceResponse
    @PostMapping(ProductApiConstants.GET_BY_ID_PATH)
    public ResponseEntity<ApiResponse<Product>> getProductById(@RequestBody ProductSearchIdRequest request) {
        logRequest("根據ID獲取產品", request.getId());
        
        Product product = productService.getProductById(request.getId());
        
        logResponse("根據ID獲取產品", product.getId());
        return success(product);
    }

    @Operation(summary = "獲取所有產品", description = "分頁查詢所有產品資料")
    @StandardSearchResponse
    @PostMapping(ProductApiConstants.GET_ALL_PATH)
    public ResponseEntity<ApiResponse<BasePageResponse<Product>>> getAllProducts(@RequestBody BasePageableRequest request) {
        logRequest("獲取所有產品", request.getPage(), request.getSize());
        
        Pageable pageable = request.toPageable();
        Page<Product> products = productService.getAllProducts(pageable);
        
        logResponse("獲取所有產品", products.getTotalElements());
        return pageSuccess(products);
    }

    @Operation(summary = "根據產品類型獲取產品", description = "查詢指定類型的產品資料")
    @StandardSearchResponse
    @PostMapping(ProductApiConstants.GET_BY_TYPE_PATH)
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByType(@RequestBody ProductSearchTypeRequest request) {
        logRequest("根據產品類型獲取產品", request.getType());
        
        List<Product> products = productService.getProductsByType(request.getType());
        
        logResponse("根據產品類型獲取產品", products.size());
        return success(products);
    }

    @Operation(summary = "根據產品狀態獲取產品", description = "查詢指定狀態的產品資料")
    @StandardSearchResponse
    @PostMapping(ProductApiConstants.GET_BY_STATUS_PATH)
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByStatus(@RequestBody ProductSearchStatusRequest request) {
        logRequest("根據產品狀態獲取產品", request.getStatus());
        
        List<Product> products = productService.getProductsByStatus(request.getStatus());
        
        logResponse("根據產品狀態獲取產品", products.size());
        return success(products);
    }

    @Operation(summary = "根據產品名稱搜索", description = "通過產品名稱進行模糊搜索")
    @StandardSearchResponse
    @PostMapping(ProductApiConstants.SEARCH_BY_NAME_PATH)
    public ResponseEntity<ApiResponse<List<Product>>> searchProductsByName(@RequestBody ProductSearchNameRequest request) {
        logRequest("根據產品名稱搜索", request.getName());
        
        List<Product> products = productService.searchProductsByName(request.getName());
        
        logResponse("根據產品名稱搜索", products.size());
        return success(products);
    }

    @Operation(summary = "多條件搜索產品", description = "根據多個條件搜索產品")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "搜索成功"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "請求參數錯誤"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "內部服務器錯誤")
    })
    @StandardSearchResponse
    @PostMapping(ProductApiConstants.SEARCH_PATH)
    public ResponseEntity<ApiResponse<BasePageResponse<Product>>> searchProducts(@RequestBody ProductSearchRequest request) {
        logRequest("多條件搜索產品", request.getName(), request.getType(), request.getStatus());
        
        Pageable pageable = request.toPageable();
        Page<Product> products = productService.searchProducts(request, pageable);
        
        logResponse("多條件搜索產品", products.getTotalElements());
        return pageSuccess(products);
    }

    @Operation(summary = "根據價格範圍獲取產品", description = "查詢指定價格範圍內的產品")
    @StandardSearchResponse
    @PostMapping(ProductApiConstants.GET_BY_PRICE_RANGE_PATH)
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByPriceRange(@RequestBody ProductSearchPriceRangeRequest request) {
        logRequest("根據價格範圍獲取產品", request.getMinPrice(), request.getMaxPrice());
        
        List<Product> products = productService.getProductsByPriceRange(request.getMinPrice(), request.getMaxPrice());
        
        logResponse("根據價格範圍獲取產品", products.size());
        return success(products);
    }

    @Operation(summary = "獲取產品統計信息", description = "獲取產品類型和狀態的統計信息")
    @StandardQueryResponse
    @PostMapping(ProductApiConstants.GET_STATISTICS_PATH)
    public ResponseEntity<ApiResponse<ProductStatistics>> getProductStatistics() {
        logRequest("獲取產品統計信息");
        
        ProductStatistics statistics = productService.getProductStatistics();
        
        logResponse("獲取產品統計信息", "統計完成");
        return success(statistics);
    }
    //endregion
}