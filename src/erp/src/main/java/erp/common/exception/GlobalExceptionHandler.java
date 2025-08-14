package erp.common.exception;

import erp.common.constant.ErrorCode;
import erp.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 處理業務邏輯異常（含客戶異常）
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e) {
        log.warn("業務異常: {} - {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage(), e.getErrorCode()));
    }

    /**
     * 處理不合法參數異常（一般框架層丟出）
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("不合法參數: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage(), ErrorCode.INVALID_ARGUMENT));
    }

    /**
     * 處理參數驗證失敗
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(Exception e) {
        Map<String, String> errors = extractFieldErrors(e);
        log.warn("參數驗證失敗: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("參數驗證失敗", ErrorCode.INVALID_ARGUMENT, errors));
    }

    /**
     * 處理約束違反異常（單個參數驗證失敗）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("參數約束違反: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("參數約束違反: " + e.getMessage(), ErrorCode.INVALID_ARGUMENT));
    }

    /**
     * 處理系統未知異常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception e) {
        log.error("系統異常", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("系統內部錯誤", ErrorCode.INTERNAL_ERROR));
    }

    /**
     * 從異常中提取欄位錯誤訊息
     */
    private Map<String, String> extractFieldErrors(Exception e) {
        Map<String, String> errors = new HashMap<>();
        if (e instanceof MethodArgumentNotValidException ex) {
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (e instanceof BindException ex) {
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        return errors;
    }
}