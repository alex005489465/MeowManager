import ApiClient from '@/library/api/apiClient';
import type { ApiResponse } from '@/library/api/apiClient';
import { PRODUCT_ENDPOINTS } from '@features/products';
import type {
  Product,
  ProductCreateRequest,
  ProductUpdateRequest,
  ProductStatusUpdateRequest,
  ProductSearchRequest,
  PageProduct,
  ProductSearchNameRequest,
  ProductSearchIdRequest,
  ProductSearchStatusRequest,
  ProductSearchTypeRequest,
  ProductSearchPriceRangeRequest,
  BasePageableRequest,
  ProductStatistics
} from '../types/product';

export class ProductApiService {
  
  // 建立產品
  static async createProduct(request: ProductCreateRequest): Promise<ApiResponse<Product>> {
    return ApiClient.post<Product>(PRODUCT_ENDPOINTS.CREATE, request);
  }

  // 更新產品
  static async updateProduct(request: ProductUpdateRequest): Promise<ApiResponse<Product>> {
    return ApiClient.post<Product>(PRODUCT_ENDPOINTS.UPDATE, request);
  }

  // 更新產品狀態
  static async updateProductStatus(request: ProductStatusUpdateRequest): Promise<ApiResponse<Product>> {
    return ApiClient.post<Product>(PRODUCT_ENDPOINTS.UPDATE_STATUS, request);
  }

  // 搜尋產品
  static async searchProducts(request: ProductSearchRequest): Promise<ApiResponse<PageProduct>> {
    return ApiClient.post<PageProduct>(PRODUCT_ENDPOINTS.SEARCH, request);
  }

  // 根據名稱搜尋產品
  static async searchProductsByName(request: ProductSearchNameRequest): Promise<ApiResponse<Product[]>> {
    return ApiClient.post<Product[]>(PRODUCT_ENDPOINTS.SEARCH_BY_NAME, request);
  }

  // 獲取所有產品
  static async getAllProducts(request?: BasePageableRequest): Promise<ApiResponse<PageProduct>> {
    return ApiClient.post<PageProduct>(PRODUCT_ENDPOINTS.GET_ALL, request || {});
  }

  // 根據ID獲取產品
  static async getProductById(request: ProductSearchIdRequest): Promise<ApiResponse<Product>> {
    return ApiClient.post<Product>(PRODUCT_ENDPOINTS.GET_BY_ID, request);
  }

  // 根據狀態獲取產品
  static async getProductsByStatus(request: ProductSearchStatusRequest): Promise<ApiResponse<Product[]>> {
    return ApiClient.post<Product[]>(PRODUCT_ENDPOINTS.GET_BY_STATUS, request);
  }

  // 根據類型獲取產品
  static async getProductsByType(request: ProductSearchTypeRequest): Promise<ApiResponse<Product[]>> {
    return ApiClient.post<Product[]>(PRODUCT_ENDPOINTS.GET_BY_TYPE, request);
  }

  // 根據價格範圍獲取產品
  static async getProductsByPriceRange(request: ProductSearchPriceRangeRequest): Promise<ApiResponse<Product[]>> {
    return ApiClient.post<Product[]>(PRODUCT_ENDPOINTS.GET_BY_PRICE_RANGE, request);
  }

  // 獲取產品統計信息
  static async getProductStatistics(): Promise<ApiResponse<ProductStatistics>> {
    return ApiClient.post<ProductStatistics>(PRODUCT_ENDPOINTS.GET_STATISTICS, {});
  }
}

export default ProductApiService;