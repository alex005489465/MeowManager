import ApiClient from '@/library/api/apiClient';
import type { ApiResponse } from '@/library/api/apiClient';
import type {
  Customer,
  CustomerCreateRequest,
  CustomerUpdateRequest,
  CustomerStatusUpdateRequest,
  CustomerSearchRequest,
  PageCustomer,
  CustomerNameRequest,
  CustomerPhoneRequest,
  CustomerEmailRequest,
  CustomerSearchIdRequest,
  CustomerBirthDateRangeRequest,
  CustomerPageableRequest,
  CustomerSearchStatusRequest
} from '../types';

export class CustomerApiService {
  
  // 建立客戶
  static async createCustomer(url: string, request: CustomerCreateRequest): Promise<ApiResponse<Customer>> {
    return ApiClient.post<Customer>(url, request);
  }

  // 更新客戶
  static async updateCustomer(url: string, request: CustomerUpdateRequest): Promise<ApiResponse<Customer>> {
    return ApiClient.post<Customer>(url, request);
  }

  // 更新客戶狀態
  static async updateCustomerStatus(url: string, request: CustomerStatusUpdateRequest): Promise<ApiResponse<Customer>> {
    return ApiClient.post<Customer>(url, request);
  }

  // 搜尋客戶
  static async searchCustomers(url: string, request: CustomerSearchRequest): Promise<ApiResponse<PageCustomer>> {
    return ApiClient.post<PageCustomer>(url, request);
  }

  // 根據名稱搜尋客戶
  static async searchCustomersByName(url: string, request: CustomerNameRequest): Promise<ApiResponse<Customer[]>> {
    return ApiClient.post<Customer[]>(url, request);
  }

  // 獲取所有客戶
  static async getAllCustomers(url: string, request?: CustomerPageableRequest): Promise<ApiResponse<PageCustomer>> {
    return ApiClient.post<PageCustomer>(url, request || {});
  }

  // 根據ID獲取客戶
  static async getCustomerById(url: string, request: CustomerSearchIdRequest): Promise<ApiResponse<Customer>> {
    return ApiClient.post<Customer>(url, request);
  }

  // 根據Email獲取客戶
  static async getCustomerByEmail(url: string, request: CustomerEmailRequest): Promise<ApiResponse<Customer>> {
    return ApiClient.post<Customer>(url, request);
  }

  // 根據電話獲取客戶
  static async getCustomerByPhone(url: string, request: CustomerPhoneRequest): Promise<ApiResponse<Customer>> {
    return ApiClient.post<Customer>(url, request);
  }

  // 根據狀態獲取客戶
  static async getCustomersByStatus(url: string, request: CustomerSearchStatusRequest): Promise<ApiResponse<PageCustomer>> {
    return ApiClient.post<PageCustomer>(url, request);
  }

  // 根據生日範圍獲取客戶
  static async getCustomersByBirthDateRange(url: string, request: CustomerBirthDateRangeRequest): Promise<ApiResponse<Customer[]>> {
    return ApiClient.post<Customer[]>(url, request);
  }

  // 獲取狀態統計
  static async getStatusStatistics(url: string): Promise<ApiResponse<Record<string, unknown>>> {
    return ApiClient.post<Record<string, unknown>>(url, {});
  }

  // 獲取最近客戶
  static async getRecentCustomers(url: string, request?: CustomerPageableRequest): Promise<ApiResponse<PageCustomer>> {
    return ApiClient.post<PageCustomer>(url, request || {});
  }
}

export default CustomerApiService;