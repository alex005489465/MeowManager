package erp.customer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定義電話號碼驗證註解
 * 支援台灣手機號碼格式：09xxxxxxxx 或 +886-9xxxxxxxx
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {
    String message() default "手機號碼格式不正確，請使用台灣手機號碼格式";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}