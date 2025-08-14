import { ErrorHandler } from '@/library/api';
import CustomerApiService from './CustomerApiService';
import { CUSTOMER_ENDPOINTS } from '@/config/endpoints';
import type {
  Customer,
  CustomerCreateRequest,
  CustomerUpdateRequest,
  CustomerStatus,
  PageCustomer
} from '../types/customer';

// 擴展Error介面以包含errorCode
interface ExtendedError extends Error {
  errorCode?: string | null;
}

export class CustomerService {
  
  // 獲取所有客戶
  static async getAllCustomers(page?: number, size?: number): Promise<Customer[]> {
    try {
      const response = await CustomerApiService.getAllCustomers(
        CUSTOMER_ENDPOINTS.GET_ALL,
        { page, size }
      );
      
      if (response.success) {
        return response.data.content;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message) as ExtendedError;
      error.errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to get all customers:', ErrorHandler.formatErrorMessage(error));
      throw error;
    }
  }

  // 獲取分頁客戶資料
  static async getCustomersPage(page?: number, size?: number): Promise<PageCustomer> {
    try {
      const response = await CustomerApiService.getAllCustomers(
        CUSTOMER_ENDPOINTS.GET_ALL,
        { page, size }
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message);
      (error as any).errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to get customers page:', ErrorHandler.formatErrorMessage(error));
      throw error;
    }
  }

  // 根據ID獲取客戶
  static async getCustomerById(id: number): Promise<Customer | null> {
    try {
      const response = await CustomerApiService.getCustomerById(
        CUSTOMER_ENDPOINTS.GET_BY_ID,
        { id }
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 記錄業務邏輯錯誤但不拋出異常
      const errorCodeMsg = response.errorCode ? ` Error Code: ${response.errorCode}` : '';
      console.warn('Get customer by id failed:', response.message + errorCodeMsg);
      return null;
    } catch (error) {
      console.error('Failed to get customer by id:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }

  // 新增客戶
  static async addCustomer(customerData: Omit<Customer, 'id' | 'createdAt' | 'updatedAt' | 'status'>): Promise<Customer | null> {
    try {
      const createRequest: CustomerCreateRequest = {
        name: customerData.name,
        nick: customerData.nick,
        gender: customerData.gender,
        birthDate: customerData.birthDate,
        fbAccount: customerData.fbAccount,
        lineAccount: customerData.lineAccount,
        email: customerData.email,
        phone: customerData.phone,
        address: customerData.address,
        note: customerData.note
      };
      
      const response = await CustomerApiService.createCustomer(
        CUSTOMER_ENDPOINTS.CREATE,
        createRequest
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message);
      (error as any).errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to add customer:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }

  // 更新客戶
  static async updateCustomer(id: number, customerData: Partial<Customer>): Promise<Customer | null> {
    try {
      const updateRequest: CustomerUpdateRequest = {
        id,
        name: customerData.name!,
        nick: customerData.nick,
        gender: customerData.gender!,
        birthDate: customerData.birthDate,
        fbAccount: customerData.fbAccount,
        lineAccount: customerData.lineAccount,
        email: customerData.email,
        phone: customerData.phone!,
        address: customerData.address!,
        note: customerData.note
      };
      
      const response = await CustomerApiService.updateCustomer(
        CUSTOMER_ENDPOINTS.UPDATE,
        updateRequest
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message);
      (error as any).errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to update customer:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }

  // 更新客戶狀態
  static async updateCustomerStatus(id: number, status: CustomerStatus): Promise<Customer | null> {
    try {
      const response = await CustomerApiService.updateCustomerStatus(
        CUSTOMER_ENDPOINTS.UPDATE_STATUS,
        { id, status }
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message);
      (error as any).errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to update customer status:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }

  // 搜尋客戶
  static async searchCustomers(searchParams: {
    name?: string;
    phone?: string;
    email?: string;
    status?: CustomerStatus;
    page?: number;
    size?: number;
  }): Promise<PageCustomer | null> {
    try {
      const response = await CustomerApiService.searchCustomers(
        CUSTOMER_ENDPOINTS.SEARCH,
        searchParams
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message);
      (error as any).errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to search customers:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }

  // 根據名稱搜尋客戶
  static async searchCustomersByName(name: string): Promise<Customer[]> {
    try {
      const response = await CustomerApiService.searchCustomersByName(
        CUSTOMER_ENDPOINTS.SEARCH_BY_NAME,
        { name }
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message);
      (error as any).errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to search customers by name:', ErrorHandler.formatErrorMessage(error));
      return [];
    }
  }

  // 根據電話獲取客戶
  static async getCustomerByPhone(phone: string): Promise<Customer | null> {
    try {
      const response = await CustomerApiService.getCustomerByPhone(
        CUSTOMER_ENDPOINTS.GET_BY_PHONE,
        { phone }
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 記錄業務邏輯錯誤但不拋出異常
      const errorCodeMsg = response.errorCode ? ` Error Code: ${response.errorCode}` : '';
      console.warn('Get customer by phone failed:', response.message + errorCodeMsg);
      return null;
    } catch (error) {
      console.error('Failed to get customer by phone:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }

  // 根據Email獲取客戶
  static async getCustomerByEmail(email: string): Promise<Customer | null> {
    try {
      const response = await CustomerApiService.getCustomerByEmail(
        CUSTOMER_ENDPOINTS.GET_BY_EMAIL,
        { email }
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 記錄業務邏輯錯誤但不拋出異常
      const errorCodeMsg = response.errorCode ? ` Error Code: ${response.errorCode}` : '';
      console.warn('Get customer by email failed:', response.message + errorCodeMsg);
      return null;
    } catch (error) {
      console.error('Failed to get customer by email:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }

  // 獲取狀態統計
  static async getStatusStatistics(): Promise<Record<string, unknown> | null> {
    try {
      const response = await CustomerApiService.getStatusStatistics(
        CUSTOMER_ENDPOINTS.GET_STATUS_STATISTICS
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message);
      (error as any).errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to get status statistics:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }

  // 獲取最近客戶
  static async getRecentCustomers(page?: number, size?: number): Promise<PageCustomer | null> {
    try {
      const response = await CustomerApiService.getRecentCustomers(
        CUSTOMER_ENDPOINTS.GET_RECENT,
        { page, size }
      );
      
      if (response.success) {
        return response.data;
      }
      
      // 處理業務邏輯錯誤
      const error = new Error(response.message);
      (error as any).errorCode = response.errorCode;
      throw error;
    } catch (error) {
      console.error('Failed to get recent customers:', ErrorHandler.formatErrorMessage(error));
      return null;
    }
  }
}

export default CustomerService;