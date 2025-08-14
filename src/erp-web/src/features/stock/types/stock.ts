// 庫存異動類型
export const MovementType = {
  IN: 'IN',
  OUT: 'OUT'
} as const;

export type MovementType = typeof MovementType[keyof typeof MovementType];

// 主要庫存接口
export interface Stock {
  id: number;
  productId: number;
  productName: string;
  qty: number;
  avgCost: number;
  totalCost: number;
  updatedAt: string;
}

export interface StockMovement {
  id: number;
  stockId: number;
  productId: number;
  productName: string;
  movementType: MovementType;
  qty: number;
  unitCost: number;
  totalCost: number;
  reason?: string;
  createdAt: string;
}

// 請求 DTOs
export interface StockInboundRequest {
  productId: number;
  qty: number;
  unitCost?: number;
  reason?: string;
}

export interface StockOutboundRequest {
  productId: number;
  qty: number;
  reason?: string;
}

export interface StockSearchRequest {
  page?: number;
  size?: number;
  productId?: number;
  productName?: string;
}

export interface StockAvailabilityRequest {
  productId: number;
  requiredQty: number;
}

export interface StockMovementSearchRequest {
  page?: number;
  size?: number;
  productId?: number;
  movementType?: MovementType;
  startDate?: string;
  endDate?: string;
}

// 響應 DTOs
export interface StockMovementResponse {
  id: number;
  stockId: number;
  productId: number;
  productName: string;
  movementType: MovementType;
  qty: number;
  unitCost: number;
  totalCost: number;
  createdAt: string;
}

export interface StockAvailabilityResponse {
  productId: number;
  currentQty: number;
  requiredQty: number;
  isAvailable: boolean;
  shortageQty: number;
}

export interface PageStock {
  totalPages: number;
  totalElements: number;
  size: number;
  content: Stock[];
  number: number;
  sort: SortObject;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  pageable: PageableObject;
  empty: boolean;
}

export interface PageStockMovement {
  totalPages: number;
  totalElements: number;
  size: number;
  content: StockMovement[];
  number: number;
  sort: SortObject;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  pageable: PageableObject;
  empty: boolean;
}

// 輔助接口
export interface SortObject {
  empty: boolean;
  unsorted: boolean;
  sorted: boolean;
}

export interface PageableObject {
  offset: number;
  sort: SortObject;
  unpaged: boolean;
  pageSize: number;
  pageNumber: number;
  paged: boolean;
}

export interface BasePageableRequest {
  page?: number;
  size?: number;
}