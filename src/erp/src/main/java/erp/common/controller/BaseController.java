package erp.common.controller;

import erp.common.dto.ApiResponse;
import erp.common.dto.BasePageResponse;
import erp.common.dto.BasePageableRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

/**
 * 控制器基類
 * 提供通用的響應處理和分頁功能
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public abstract class BaseController<T, ID> {

    /**
     * 成功響應包裝
     */
    protected <R> ResponseEntity<ApiResponse<R>> success(R data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * 成功響應包裝（帶消息）
     */
    protected <R> ResponseEntity<ApiResponse<R>> success(String message, R data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    /**
     * 創建成功響應包裝
     */
    protected <R> ResponseEntity<ApiResponse<R>> created(String message, R data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(message, data));
    }

    /**
     * 分頁響應包裝
     */
    protected <R> ResponseEntity<ApiResponse<BasePageResponse<R>>> pageSuccess(Page<R> page) {
        return ResponseEntity.ok(ApiResponse.success(BasePageResponse.of(page)));
    }

    /**
     * 創建Pageable對象的通用方法
     */
    protected Pageable createPageable(BasePageableRequest request) {
        return request.toPageable();
    }

    /**
     * 記錄請求日誌的通用方法
     */
    protected void logRequest(String operation, Object... params) {
        log.info("{}請求: {}", operation, params);
    }

    /**
     * 記錄響應日誌的通用方法
     */
    protected void logResponse(String operation, Object result) {
        log.info("{}成功: {}", operation, result);
    }

    // 抽象方法：子類必須實現的核心業務方法
    // 注意：子類需要根據自己的請求對象類型來實現這些方法
    // 這裡不強制實現抽象方法，讓子類靈活處理
}