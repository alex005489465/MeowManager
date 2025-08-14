// Product Status constants and type
export const ProductStatus = {
  ACTIVE: 'ACTIVE',
  INACTIVE: 'INACTIVE',
  DISCONTINUED: 'DISCONTINUED'
} as const;

export type ProductStatus = typeof ProductStatus[keyof typeof ProductStatus];

// Product Type constants and type
export const ProductType = {
  PHYSICAL: 'PHYSICAL',
  DIGITAL: 'DIGITAL',
  SERVICE: 'SERVICE'
} as const;

export type ProductType = typeof ProductType[keyof typeof ProductType];

// Main Product interface
export interface Product {
  id: number;
  name: string;
  description?: string;
  type: ProductType;
  price: number;
  cost?: number;
  sku?: string;
  barcode?: string;
  stock?: number;
  minStock?: number;
  maxStock?: number;
  status: ProductStatus;
  createdAt: string;
  updatedAt: string;
}

// Request DTOs
export interface ProductCreateRequest {
  name: string;
  description?: string;
  type: ProductType;
  price: number;
  cost?: number;
  sku?: string;
  barcode?: string;
  stock?: number;
  minStock?: number;
  maxStock?: number;
}

export interface ProductUpdateRequest {
  id: number;
  name: string;
  description?: string;
  type: ProductType;
  price: number;
  cost?: number;
  sku?: string;
  barcode?: string;
  stock?: number;
  minStock?: number;
  maxStock?: number;
}

export interface ProductStatusUpdateRequest {
  id: number;
  status: ProductStatus;
}

export interface ProductSearchRequest {
  page?: number;
  size?: number;
  name?: string;
  type?: ProductType;
  status?: ProductStatus;
  minPrice?: number;
  maxPrice?: number;
  sku?: string;
  barcode?: string;
}

export interface ProductSearchNameRequest {
  name: string;
}

export interface ProductSearchIdRequest {
  id: number;
}

export interface ProductSearchStatusRequest {
  page?: number;
  size?: number;
  status: ProductStatus;
}

export interface ProductSearchTypeRequest {
  page?: number;
  size?: number;
  type: ProductType;
}

export interface ProductSearchPriceRangeRequest {
  page?: number;
  size?: number;
  minPrice: number;
  maxPrice: number;
}

export interface BasePageableRequest {
  page?: number;
  size?: number;
}

// Response DTOs
export interface PageProduct {
  totalPages: number;
  totalElements: number;
  size: number;
  content: Product[];
  number: number;
  sort: SortObject;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  pageable: PageableObject;
  empty: boolean;
}

export interface ProductStatistics {
  totalProducts: number;
  activeProducts: number;
  inactiveProducts: number;
  discontinuedProducts: number;
  productsByType: Record<ProductType, number>;
  lowStockProducts: number;
  totalValue: number;
}

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