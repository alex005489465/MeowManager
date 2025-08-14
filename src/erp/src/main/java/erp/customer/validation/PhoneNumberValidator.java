package erp.customer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 電話號碼格式驗證器
 * 支援台灣手機號碼格式：09xxxxxxxx 或 +886-9xxxxxxxx 或 886-9xxxxxxxx
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        // 初始化方法，可以在這裡設置驗證參數
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || phone.trim().isEmpty()) {
            return true; // 空值由 @NotBlank 處理
        }
        
        // 台灣手機號碼正則表達式
        // 支援格式：09xxxxxxxx, +886-9xxxxxxxx, +8869xxxxxxxx, 886-9xxxxxxxx, 8869xxxxxxxx
        String phoneRegex = "^(09\\d{8}|\\+886-?9\\d{8}|886-?9\\d{8})$";
        return phone.matches(phoneRegex);
    }
}