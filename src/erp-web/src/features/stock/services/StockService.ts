import { StockApiService } from './StockApiService';

export class StockService {
  /**
   * 根據產品ID獲取產品庫存數量
   * @param productId 產品ID
   * @returns Promise<number> 庫存數量，如果獲取失敗返回0
   */
  static async getProductStockQuantity(productId: number): Promise<number> {
    try {
      const response = await StockApiService.getStockByProduct(productId);
      if (response.success && response.data) {
        return response.data.qty;
      }
      return 0; // 如果沒有庫存記錄，返回0
    } catch (error) {
      console.error(`獲取產品 ${productId} 庫存失敗:`, error);
      return 0;
    }
  }
}

export default StockService;