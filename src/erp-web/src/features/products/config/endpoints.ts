import { SERVER_URL } from '@/config/endpoints';

// Product API endpoints
export const PRODUCT_ENDPOINTS = {
  // Product CRUD operations
  CREATE: `${SERVER_URL}/api/products/create`,
  UPDATE: `${SERVER_URL}/api/products/update`,
  UPDATE_STATUS: `${SERVER_URL}/api/products/updateStatus`,
  
  // Product search operations
  SEARCH: `${SERVER_URL}/api/products/search`,
  SEARCH_BY_NAME: `${SERVER_URL}/api/products/searchByName`,
  GET_ALL: `${SERVER_URL}/api/products/getAll`,
  
  // Product retrieval operations
  GET_BY_ID: `${SERVER_URL}/api/products/getById`,
  GET_BY_STATUS: `${SERVER_URL}/api/products/getByStatus`,
  GET_BY_TYPE: `${SERVER_URL}/api/products/getByType`,
  GET_BY_PRICE_RANGE: `${SERVER_URL}/api/products/getByPriceRange`,
  
  // Product statistics
  GET_STATISTICS: `${SERVER_URL}/api/products/getStatistics`,
} as const;