// 測試API工具的基本功能
import { ApiClient, ErrorHandler, ApiError, FetchError } from './index';
import type { ApiResponse } from './index';
import CustomerApiService from '@features/customers/services/CustomerApiService';
import { CUSTOMER_ENDPOINTS } from '@/config/endpoints';

// 測試API客戶端基本功能
async function testApiClient() {
  console.log('Testing ApiClient...');
  
  try {
    // 測試基本POST請求（這會失敗，因為沒有真實的服務器）
    const testUrl = 'http://localhost:8080/test';
    const testData = { test: 'data' };
    
    const response = await ApiClient.post(testUrl, testData);
    console.log('API Response:', response);
  } catch (error) {
    console.log('Expected error caught:', ErrorHandler.formatErrorMessage(error));
    
    // 測試錯誤處理功能
    if (ErrorHandler.isNetworkError(error)) {
      console.log('This is a network error');
    }
    if (ErrorHandler.isHttpError(error)) {
      console.log('This is an HTTP error');
    }
  }
}

// 測試客戶API功能
async function testCustomerApiService() {
  console.log('Testing CustomerApiService...');
  
  try {
    // 測試獲取所有客戶（這會失敗，因為沒有真實的服務器）
    const response = await CustomerApiService.getAllCustomers(CUSTOMER_ENDPOINTS.GET_ALL);
    console.log('Customer API Response:', response);
  } catch (error) {
    console.log('Expected customer API error:', ErrorHandler.formatErrorMessage(error));
  }
}

// 測試錯誤處理功能
function testErrorHandler() {
  console.log('Testing ErrorHandler...');
  
  // 測試API錯誤
  const apiError = new ApiError('Test API error', 'TEST_ERROR');
  console.log('API Error formatted:', ErrorHandler.formatErrorMessage(apiError));
  console.log('Has error code:', ErrorHandler.hasErrorCode(apiError));
  console.log('Is business error:', ErrorHandler.isBusinessError(apiError));
  
  // 測試Fetch錯誤
  const fetchError = new FetchError('Test fetch error', 404, 'Not Found', 'NOT_FOUND');
  console.log('Fetch Error formatted:', ErrorHandler.formatErrorMessage(fetchError));
  console.log('Is HTTP error:', ErrorHandler.isHttpError(fetchError));
  console.log('Error status:', ErrorHandler.getErrorStatus(fetchError));
}

// 測試API響應輔助方法
function testApiResponseHelpers() {
  console.log('Testing API Response helpers...');
  
  // 測試成功響應
  const successResponse: ApiResponse<string> = {
    success: true,
    message: 'Success',
    data: 'test data',
    errorCode: null
  };
  
  console.log('Is success:', ApiClient.isSuccess(successResponse));
  console.log('Error message:', ApiClient.getErrorMessage(successResponse));
  console.log('Error code:', ApiClient.getErrorCode(successResponse));
  console.log('Has error code:', ApiClient.hasErrorCode(successResponse));
  
  // 測試失敗響應
  const errorResponse: ApiResponse<string> = {
    success: false,
    message: 'Error occurred',
    data: '',
    errorCode: 'VALIDATION_ERROR'
  };
  
  console.log('Is success:', ApiClient.isSuccess(errorResponse));
  console.log('Error message:', ApiClient.getErrorMessage(errorResponse));
  console.log('Error code:', ApiClient.getErrorCode(errorResponse));
  console.log('Has error code:', ApiClient.hasErrorCode(errorResponse));
}

// 執行所有測試
export async function runTests() {
  console.log('=== API Tools Test Suite ===');
  
  testErrorHandler();
  testApiResponseHelpers();
  
  // 這些測試會產生網路錯誤，但這是預期的
  await testApiClient();
  await testCustomerApiService();
  
  console.log('=== Test Suite Complete ===');
}

// 測試函數可以被其他模組調用
// runTests().catch(console.error);