import { SERVER_URL } from '@/config/endpoints';

// 庫存 API 端點配置
export const STOCK_ENDPOINTS = {
  // 庫存異動操作
  INBOUND: `${SERVER_URL}/api/stocks/inbound`,
  OUTBOUND: `${SERVER_URL}/api/stocks/outbound`,
  
  // 庫存查詢操作
  GET_STOCKS: `${SERVER_URL}/api/stocks/query`,
  GET_STOCK_BY_PRODUCT: `${SERVER_URL}/api/stocks/by-id`,
  CHECK_AVAILABILITY: `${SERVER_URL}/api/stocks/availability`,
  
  // 庫存異動記錄
  GET_MOVEMENTS: `${SERVER_URL}/api/stocks/getMovements`,
  GET_MOVEMENTS_BY_PRODUCT: `${SERVER_URL}/api/stocks/getMovementsByProduct`,
} as const;