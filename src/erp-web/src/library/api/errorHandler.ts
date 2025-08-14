import type { ApiResponse } from './apiClient';
import { FetchError } from './apiClient';

// 自定義API錯誤類別
export class ApiError extends Error {
  public readonly errorCode: string | null;
  public readonly success: boolean;

  constructor(message: string, errorCode: string | null, success: boolean = false) {
    super(message);
    this.name = 'ApiError';
    this.errorCode = errorCode;
    this.success = success;
  }
}

// 錯誤處理工具
export class ErrorHandler {
  
  // 處理API響應錯誤
  static handleApiResponse<T>(response: ApiResponse<T>): T {
    if (response.success) {
      return response.data;
    }
    
    throw new ApiError(response.message, response.errorCode, response.success);
  }

  // 檢查並處理響應
  static checkResponse<T>(response: ApiResponse<T>): boolean {
    return response.success === true;
  }

  // 格式化錯誤訊息
  static formatErrorMessage(error: unknown): string {
    if (error instanceof ApiError) {
      if (error.errorCode) {
        return `${error.message} (錯誤代碼: ${error.errorCode})`;
      } else {
        return error.message;
      }
    }
    
    if (error instanceof FetchError) {
      let message = error.message;
      if (error.status) {
        message += ` (HTTP ${error.status})`;
      }
      if (error.errorCode) {
        message += ` (錯誤代碼: ${error.errorCode})`;
      }
      return message;
    }
    
    if (error instanceof Error) {
      return error.message;
    }
    
    return 'Unknown error occurred';
  }

  // 根據錯誤代碼進行特殊處理
  static handleErrorByCode(errorCode: string | null): void {
    if (!errorCode) {
      console.warn('No error code provided');
      return;
    }

    switch (errorCode) {
      case 'UNAUTHORIZED':
        console.warn('User unauthorized, redirecting to login...');
        // 可以在這裡添加跳轉到登入頁面的邏輯
        break;
      case 'FORBIDDEN':
        console.warn('Access forbidden');
        break;
      case 'NOT_FOUND':
        console.warn('Resource not found');
        break;
      case 'VALIDATION_ERROR':
        console.warn('Validation error occurred');
        break;
      case 'TIMEOUT':
        console.warn('Request timeout');
        break;
      default:
        console.warn('Unknown error code:', errorCode);
    }
  }

  // 檢查錯誤是否有錯誤代碼
  static hasErrorCode(error: ApiError | FetchError): boolean {
    return error.errorCode !== null && error.errorCode !== undefined;
  }

  // 判斷是否為網路錯誤
  static isNetworkError(error: unknown): boolean {
    return error instanceof FetchError && 
           (error.message.includes('Network Error') || error.message.includes('timeout'));
  }

  // 判斷是否為HTTP錯誤
  static isHttpError(error: unknown): boolean {
    return error instanceof FetchError && error.status !== undefined;
  }

  // 判斷是否為業務邏輯錯誤
  static isBusinessError(error: unknown): boolean {
    return error instanceof ApiError;
  }

  // 獲取錯誤狀態碼
  static getErrorStatus(error: unknown): number | undefined {
    if (error instanceof FetchError) {
      return error.status;
    }
    return undefined;
  }

  // 獲取錯誤代碼
  static getErrorCode(error: unknown): string | null {
    if (error instanceof ApiError || error instanceof FetchError) {
      return error.errorCode || null;
    }
    return null;
  }

  // 判斷是否為取消請求錯誤
  static isAbortError(error: unknown): boolean {
    return error instanceof FetchError && error.message.includes('timeout or cancelled');
  }
}

export default ErrorHandler;