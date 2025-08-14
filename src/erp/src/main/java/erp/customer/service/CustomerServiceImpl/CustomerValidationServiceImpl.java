package erp.customer.service.CustomerServiceImpl;

import erp.common.constant.ErrorCode;
import erp.common.exception.CustomerException;
import erp.customer.constant.CustomerConstants;
import erp.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 客戶驗證功能實現
 * 從原 CustomerValidationService 遷移而來
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerValidationServiceImpl {

    private final CustomerQueryServiceImpl queryService;

    /**
     * 驗證客戶資料
     */
    public void validateCustomer(Customer customer) {
        // 姓名的空值驗證已移至DTO層處理
        // 長度驗證已由@Size註解處理，這裡保留作為額外檢查
        if (customer.getName() != null && customer.getName().length() > CustomerConstants.NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(CustomerConstants.NAME_LENGTH_EXCEEDED);
        }
        
        if (StringUtils.hasText(customer.getEmail()) && customer.getEmail().length() > CustomerConstants.EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException(CustomerConstants.EMAIL_LENGTH_EXCEEDED);
        }
        
        if (StringUtils.hasText(customer.getPhone()) && customer.getPhone().length() > CustomerConstants.PHONE_MAX_LENGTH) {
            throw new IllegalArgumentException(CustomerConstants.PHONE_LENGTH_EXCEEDED);
        }
        
        // 新增電話號碼格式驗證
        validatePhoneFormat(customer.getPhone());
        
        // 新增地址格式驗證
        validateAddressFormat(customer.getAddress());
        
        // 標準化電話號碼
        if (StringUtils.hasText(customer.getPhone())) {
            customer.setPhone(normalizePhoneNumber(customer.getPhone()));
        }
    }

    /**
     * 驗證電話號碼格式
     * 支援台灣手機號碼格式：09xxxxxxxx 或 +886-9xxxxxxxx
     */
    public void validatePhoneFormat(String phone) {
        if (!StringUtils.hasText(phone)) {
            return;
        }
        
        if (!phone.matches(CustomerConstants.TAIWAN_PHONE_REGEX)) {
            throw new IllegalArgumentException(CustomerConstants.INVALID_PHONE_FORMAT);
        }
    }

    /**
     * 標準化電話號碼格式
     * 將不同格式的電話號碼統一為 09xxxxxxxx 格式
     */
    public String normalizePhoneNumber(String phone) {
        if (!StringUtils.hasText(phone)) {
            return phone;
        }
        
        // 移除所有非數字字符
        String cleanPhone = phone.replaceAll("[^0-9]", "");
        
        // 處理 +886 開頭的號碼
        if (cleanPhone.startsWith("886") && cleanPhone.length() == 12) {
            return "0" + cleanPhone.substring(3);
        }
        
        return cleanPhone;
    }

    /**
     * 驗證地址格式
     * 檢查地址的基本結構和長度
     */
    public void validateAddressFormat(String address) {
        if (!StringUtils.hasText(address)) {
            return;
        }
        
        // 地址長度檢查
        if (address.length() > CustomerConstants.ADDRESS_MAX_LENGTH) {
            throw new IllegalArgumentException(CustomerConstants.ADDRESS_LENGTH_EXCEEDED);
        }
        
        // 地址最小長度檢查
        if (address.trim().length() < CustomerConstants.ADDRESS_MIN_LENGTH) {
            throw new IllegalArgumentException(CustomerConstants.ADDRESS_TOO_SHORT);
        }
        
        // 檢查是否包含基本地址元素（縣市、區域等）
        boolean hasAddressElement = false;
        
        for (String keyword : CustomerConstants.ADDRESS_KEYWORDS) {
            if (address.contains(keyword)) {
                hasAddressElement = true;
                break;
            }
        }
        
        if (!hasAddressElement) {
            throw new IllegalArgumentException(CustomerConstants.INCOMPLETE_ADDRESS_FORMAT);
        }
    }

    /**
     * 驗證手機號碼唯一性
     * @param phone 手機號碼
     * @param excludeCustomerId 排除的客戶ID（用於更新時排除自己）
     */
    public void validatePhoneUniqueness(String phone, Long excludeCustomerId) {
        if (!StringUtils.hasText(phone)) {
            return;
        }
        
        queryService.getCustomerByPhone(phone)
            .ifPresent(existingCustomer -> {
                // 如果是更新操作且是同一個客戶，則允許
                if (existingCustomer.getId().equals(excludeCustomerId)) {
                    return;
                }
                throw new CustomerException(CustomerConstants.PHONE_ALREADY_EXISTS + phone, ErrorCode.PHONE_ALREADY_EXISTS);
            });
    }

    /**
     * 驗證Email唯一性
     * @param email Email地址
     * @param excludeCustomerId 排除的客戶ID（用於更新時排除自己）
     */
    public void validateEmailUniqueness(String email, Long excludeCustomerId) {
        if (!StringUtils.hasText(email)) {
            return;
        }
        
        queryService.getCustomerByEmail(email)
            .ifPresent(existingCustomer -> {
                // 如果是更新操作且是同一個客戶，則允許
                if (existingCustomer.getId().equals(excludeCustomerId)) {
                    return;
                }
                throw new CustomerException(CustomerConstants.EMAIL_ALREADY_EXISTS + email, ErrorCode.EMAIL_ALREADY_EXISTS);
            });
    }

    /**
     * 驗證客戶是否存在
     * @param customerId 客戶ID
     */
    public void validateCustomerExists(Long customerId) {
        // 這個方法會在客戶不存在時拋出異常
        queryService.getCustomerById(customerId);
    }
}