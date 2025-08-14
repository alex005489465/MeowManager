// 通用API響應介面 - 匹配後端格式
export interface ApiResponse<T = any> {
  success: boolean;        // 操作是否成功
  message: string;         // 操作訊息
  data: T;                // 響應資料
  errorCode: string | null; // 錯誤代碼 - 可能為null
}

// API配置選項
export interface ApiOptions {
  timeout?: number;        // 請求超時時間（毫秒）
  headers?: Record<string, string>; // 自定義請求頭
  signal?: AbortSignal;    // 取消請求信號
}

// 自定義錯誤類別
export class FetchError extends Error {
  public readonly status?: number;
  public readonly statusText?: string;
  public readonly errorCode?: string | null;

  constructor(message: string, status?: number, statusText?: string, errorCode?: string | null) {
    super(message);
    this.name = 'FetchError';
    this.status = status;
    this.statusText = statusText;
    this.errorCode = errorCode;
  }
}

// API客戶端工具類
export class ApiClient {
  
  // 預設配置
  private static readonly DEFAULT_TIMEOUT = 10000; // 10秒
  private static readonly DEFAULT_HEADERS = {
    'Content-Type': 'application/json',
  };

  // POST請求 - 統一的API呼叫方法
  static async post<T>(url: string, data?: any, options?: ApiOptions): Promise<ApiResponse<T>> {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), options?.timeout || this.DEFAULT_TIMEOUT);

    try {
      console.log('API Request: POST', url);
      
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          ...this.DEFAULT_HEADERS,
          ...options?.headers,
        },
        body: data ? JSON.stringify(data) : undefined,
        signal: options?.signal || controller.signal,
      });

      clearTimeout(timeoutId);

      console.log('API Response:', response.status, url);

      // 檢查HTTP狀態
      if (!response.ok) {
        await this.handleHttpError(response);
      }

      // 解析響應資料
      const responseData: ApiResponse<T> = await response.json();
      
      // 檢查業務邏輯是否成功
      if (!responseData.success) {
        console.warn('API Business Logic Error:', responseData.message, 'Error Code:', responseData.errorCode);
      }

      return responseData;

    } catch (error) {
      clearTimeout(timeoutId);
      throw this.handleError(error);
    }
  }

  // 處理HTTP錯誤
  private static async handleHttpError(response: Response): Promise<never> {
    let errorMessage = `HTTP Error: ${response.status} ${response.statusText}`;
    let errorCode: string | null = null;

    try {
      // 嘗試解析錯誤響應
      const errorData = await response.json();
      if (errorData && typeof errorData === 'object') {
        if ('message' in errorData) {
          errorMessage = errorData.message;
        }
        if ('errorCode' in errorData) {
          errorCode = errorData.errorCode;
        }
      }
    } catch {
      // 如果無法解析JSON，使用預設錯誤訊息
    }

    throw new FetchError(errorMessage, response.status, response.statusText, errorCode);
  }

  // 統一錯誤處理
  private static handleError(error: any): FetchError {
    if (error instanceof FetchError) {
      return error;
    }

    if (error.name === 'AbortError') {
      return new FetchError('Request timeout or cancelled');
    }

    if (error instanceof TypeError && error.message.includes('fetch')) {
      return new FetchError('Network Error: Unable to connect to server');
    }

    return new FetchError(error.message || 'Unknown error occurred');
  }

  // 檢查響應是否成功的輔助方法
  static isSuccess<T>(response: ApiResponse<T>): boolean {
    return response.success === true;
  }

  // 獲取錯誤訊息的輔助方法
  static getErrorMessage<T>(response: ApiResponse<T>): string {
    return response.message || 'Unknown error';
  }

  // 獲取錯誤代碼的輔助方法
  static getErrorCode<T>(response: ApiResponse<T>): string | null {
    return response.errorCode;
  }

  // 檢查是否有錯誤代碼
  static hasErrorCode<T>(response: ApiResponse<T>): boolean {
    return response.errorCode !== null && response.errorCode !== undefined;
  }
}

export default ApiClient;