import ApiClient from '@/library/api/apiClient';
import type { ApiResponse } from '@/library/api/apiClient';
import { STOCK_ENDPOINTS } from '../config/endpoints';
import type {
  StockInboundRequest,
  StockOutboundRequest,
  StockMovementResponse,
  StockSearchRequest,
  StockAvailabilityRequest,
  StockAvailabilityResponse,
  StockMovementSearchRequest,
  PageStock,
  PageStockMovement,
  BasePageableRequest,
  Stock
} from '../types/stock';

export class StockApiService {
  
  // 庫存入庫
  static async processInbound(request: StockInboundRequest): Promise<ApiResponse<StockMovementResponse>> {
    return ApiClient.post<StockMovementResponse>(STOCK_ENDPOINTS.INBOUND, request);
  }

  // 庫存出庫
  static async processOutbound(request: StockOutboundRequest): Promise<ApiResponse<StockMovementResponse>> {
    return ApiClient.post<StockMovementResponse>(STOCK_ENDPOINTS.OUTBOUND, request);
  }

  // 獲取庫存列表
  static async getStocks(request?: StockSearchRequest): Promise<ApiResponse<PageStock>> {
    return ApiClient.post<PageStock>(STOCK_ENDPOINTS.GET_STOCKS, request || {});
  }

  // 根據商品獲取庫存
  static async getStockByProduct(productId: number): Promise<ApiResponse<Stock>> {
    return ApiClient.post<Stock>(STOCK_ENDPOINTS.GET_STOCK_BY_PRODUCT, { productId });
  }

  // 檢查庫存可用性
  static async checkAvailability(request: StockAvailabilityRequest): Promise<ApiResponse<StockAvailabilityResponse>> {
    return ApiClient.post<StockAvailabilityResponse>(STOCK_ENDPOINTS.CHECK_AVAILABILITY, request);
  }

  // 獲取庫存異動記錄
  static async getMovements(request?: StockMovementSearchRequest): Promise<ApiResponse<PageStockMovement>> {
    return ApiClient.post<PageStockMovement>(STOCK_ENDPOINTS.GET_MOVEMENTS, request || {});
  }

  // 根據商品獲取異動記錄
  static async getMovementsByProduct(productId: number, request?: BasePageableRequest): Promise<ApiResponse<PageStockMovement>> {
    return ApiClient.post<PageStockMovement>(
      STOCK_ENDPOINTS.GET_MOVEMENTS_BY_PRODUCT, 
      { productId, ...request }
    );
  }
}

export default StockApiService;