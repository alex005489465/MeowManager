# API呼叫工具文檔

## 概述

本目錄包含了完整的API呼叫工具，基於原生fetch API實現，無需外部依賴。所有API呼叫都使用POST方法，支援errorCode為null或字串的後端響應格式。

## 檔案結構

```
src/library/api/
├── apiClient.ts      # 核心API客戶端
├── errorHandler.ts   # 錯誤處理工具
├── index.ts         # 統一匯出
├── test.ts          # 測試套件
└── README.md        # 本文檔

src/services/
├── CustomerApiService.ts  # 客戶相關API方法（已移至此處）
├── customerService.ts     # 客戶服務層
└── fakeData.ts           # 測試資料
```

## 核心功能

### ApiClient (apiClient.ts)

基於原生fetch的API客戶端，提供以下功能：

- **統一POST方法**：所有API呼叫都使用POST方法
- **超時控制**：預設10秒超時，可自定義
- **請求取消**：支援AbortController取消請求
- **錯誤處理**：統一處理HTTP錯誤和網路錯誤
- **日誌記錄**：自動記錄請求和響應日誌

#### 基本使用

```typescript
import { ApiClient } from '../library/api';

// 基本POST請求
const response = await ApiClient.post('http://localhost:8080/api/endpoint', data);

// 帶自定義配置
const response = await ApiClient.post(url, data, {
  timeout: 15000,
  headers: { 'Authorization': 'Bearer token' }
});
```

### CustomerApiService (已移至 src/services/CustomerApiService.ts)

客戶相關的API方法已移至services目錄，提供所有客戶相關的API方法：

- `createCustomer()` - 建立客戶
- `updateCustomer()` - 更新客戶
- `updateCustomerStatus()` - 更新客戶狀態
- `getAllCustomers()` - 獲取所有客戶
- `getCustomerById()` - 根據ID獲取客戶
- `searchCustomers()` - 搜尋客戶
- `searchCustomersByName()` - 根據名稱搜尋
- 等等...

#### 使用範例

```typescript
import CustomerApiService from '../services/CustomerApiService';
import { CUSTOMER_ENDPOINTS } from '../config/endpoints';

// 獲取所有客戶
const response = await CustomerApiService.getAllCustomers(
  CUSTOMER_ENDPOINTS.GET_ALL,
  { page: 0, size: 10 }
);

// 建立客戶
const newCustomer = await CustomerApiService.createCustomer(
  CUSTOMER_ENDPOINTS.CREATE,
  customerData
);
```

### ErrorHandler (errorHandler.ts)

提供完整的錯誤處理工具：

- `formatErrorMessage()` - 格式化錯誤訊息
- `handleErrorByCode()` - 根據錯誤代碼處理
- `isNetworkError()` - 判斷是否為網路錯誤
- `isHttpError()` - 判斷是否為HTTP錯誤
- `isBusinessError()` - 判斷是否為業務邏輯錯誤

#### 使用範例

```typescript
import { ErrorHandler } from '../library/api';

try {
  const response = await ApiClient.post(url, data);
} catch (error) {
  console.error('Error:', ErrorHandler.formatErrorMessage(error));
  
  if (ErrorHandler.isNetworkError(error)) {
    // 處理網路錯誤
  }
  
  ErrorHandler.handleErrorByCode(ErrorHandler.getErrorCode(error));
}
```

## API響應格式

所有API響應都遵循統一格式：

```typescript
interface ApiResponse<T> {
  success: boolean;        // 操作是否成功
  message: string;         // 操作訊息
  data: T;                // 響應資料
  errorCode: string | null; // 錯誤代碼（可能為null）
}
```

## 服務層整合

`CustomerService` 展示了如何在服務層中使用API工具：

```typescript
import { ErrorHandler } from '../library/api';
import CustomerApiService from './CustomerApiService';

export class CustomerService {
  static async getAllCustomers(): Promise<Customer[]> {
    try {
      const response = await CustomerApiService.getAllCustomers(CUSTOMER_ENDPOINTS.GET_ALL);
      
      if (response.success) {
        return response.data.content;
      }
      
      throw new Error(response.message);
    } catch (error) {
      console.error('Failed:', ErrorHandler.formatErrorMessage(error));
      throw error;
    }
  }
}
```

## 特色功能

### 1. 無外部依賴
- 完全基於原生fetch API
- 不需要安裝axios或其他HTTP客戶端

### 2. TypeScript支援
- 完整的類型定義
- 泛型支援，確保類型安全

### 3. 錯誤處理
- 統一的錯誤處理機制
- 支援HTTP錯誤、網路錯誤、業務邏輯錯誤
- 錯誤代碼支援null值

### 4. 靈活配置
- 自定義超時時間
- 自定義請求頭
- 請求取消支援

### 5. 日誌記錄
- 自動記錄API請求和響應
- 便於調試和監控

## 測試

執行測試套件：

```typescript
import { runTests } from '../library/api/test';

// 執行所有測試
await runTests();
```

## 注意事項

1. **URL傳遞**：所有API方法都需要傳入完整的URL
2. **POST方法**：所有API呼叫都使用POST方法
3. **錯誤處理**：建議在服務層統一處理錯誤
4. **類型安全**：充分利用TypeScript的類型檢查

## 擴展性

可以輕鬆添加新的業務模組API：

```typescript
// 在index.ts中添加新的API模組
export { default as ProductApi } from './productApi';
export { default as OrderApi } from './orderApi';
```

這套API呼叫工具提供了完整、可靠、易用的HTTP客戶端功能，完全符合專案需求。