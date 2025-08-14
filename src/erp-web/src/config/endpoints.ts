// Base server URL
export const SERVER_URL = ''; //開發用

// Customer API endpoints
export const CUSTOMER_ENDPOINTS = {
  // Customer CRUD operations
  CREATE: `${SERVER_URL}/api/customers/create`,
  UPDATE: `${SERVER_URL}/api/customers/update`,
  UPDATE_STATUS: `${SERVER_URL}/api/customers/updateStatus`,
  
  // Customer search operations
  SEARCH: `${SERVER_URL}/api/customers/search`,
  SEARCH_BY_NAME: `${SERVER_URL}/api/customers/searchByName`,
  GET_ALL: `${SERVER_URL}/api/customers/getAll`,
  
  // Customer retrieval operations
  GET_BY_ID: `${SERVER_URL}/api/customers/getById`,
  GET_BY_EMAIL: `${SERVER_URL}/api/customers/getByEmail`,
  GET_BY_PHONE: `${SERVER_URL}/api/customers/getByPhone`,
  GET_BY_STATUS: `${SERVER_URL}/api/customers/getByStatus`,
  GET_BY_BIRTH_DATE_RANGE: `${SERVER_URL}/api/customers/getByBirthDateRange`,
  
  // Customer statistics and recent data
  GET_STATUS_STATISTICS: `${SERVER_URL}/api/customers/getStatusStatistics`,
  GET_RECENT: `${SERVER_URL}/api/customers/getRecent`,
} as const;

// Export all endpoints as a flat object for easier access
export const API_ENDPOINTS = {
  ...CUSTOMER_ENDPOINTS,
} as const;

// Type for endpoint keys
export type CustomerEndpointKey = keyof typeof CUSTOMER_ENDPOINTS;
export type ApiEndpointKey = keyof typeof API_ENDPOINTS;