package erp.common.exception;

/**
 * 客戶相關異常
 */
public class CustomerException extends BusinessException {
    public CustomerException(String message, String errorCode) {
        super(message, errorCode);
    }
}