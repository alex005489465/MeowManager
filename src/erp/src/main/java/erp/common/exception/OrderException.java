package erp.common.exception;

/**
 * 訂單相關異常
 */
public class OrderException extends BusinessException {
    public OrderException(String message, String errorCode) {
        super(message, errorCode);
    }
}