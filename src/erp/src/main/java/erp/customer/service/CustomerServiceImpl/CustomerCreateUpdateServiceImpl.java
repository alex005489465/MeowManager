package erp.customer.service.CustomerServiceImpl;

import erp.customer.constant.CustomerConstants;
import erp.customer.dto.CustomerCreateRequest;
import erp.customer.entity.Customer;
import erp.customer.enums.CustomerStatus;
import erp.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客戶創建更新功能實現
 * 從原 CustomerCreateUpdateService 遷移而來
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerCreateUpdateServiceImpl {

    private final CustomerRepository customerRepository;
    private final CustomerQueryServiceImpl queryService;
    private final CustomerValidationServiceImpl validationService;

    /**
     * 創建客戶 (使用DTO)
     */
    public Customer createCustomer(CustomerCreateRequest request) {
        log.info(CustomerConstants.LOG_CREATE_CUSTOMER, request.getName());
        
        // 將DTO轉換為Customer實體
        Customer customer = Customer.builder()
                .name(request.getName())
                .nick(request.getNick())
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .fbAccount(request.getFbAccount())
                .lineAccount(request.getLineAccount())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .note(request.getNote())
                .build();
        
        // 調用現有的創建邏輯
        return createCustomer(customer);
    }

    /**
     * 創建客戶
     */
    public Customer createCustomer(Customer customer) {
        log.info(CustomerConstants.LOG_CREATE_CUSTOMER, customer.getName());
        
        // 驗證必要欄位
        validationService.validateCustomer(customer);
        
        // 檢查手機號碼是否已存在
        validationService.validatePhoneUniqueness(customer.getPhone(), null);
        
        // 檢查Email是否已存在
        validationService.validateEmailUniqueness(customer.getEmail(), null);
        
        // 設置預設狀態
        if (customer.getStatus() == null) {
            customer.setStatus(CustomerStatus.ACTIVE);
        }
        
        Customer savedCustomer = customerRepository.save(customer);
        log.info(CustomerConstants.LOG_CUSTOMER_CREATE_SUCCESS, savedCustomer.getId());
        return savedCustomer;
    }

    /**
     * 更新客戶
     */
    public Customer updateCustomer(Long id, Customer customer) {
        log.info(CustomerConstants.LOG_UPDATE_CUSTOMER, id);
        
        Customer existingCustomer = queryService.getCustomerById(id);
        
        // 驗證必要欄位
        validationService.validateCustomer(customer);
        
        // 檢查手機號碼是否被其他客戶使用
        validationService.validatePhoneUniqueness(customer.getPhone(), id);
        
        // 檢查Email是否被其他客戶使用
        validationService.validateEmailUniqueness(customer.getEmail(), id);
        
        // 更新欄位
        updateCustomerFields(existingCustomer, customer);
        
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        log.info(CustomerConstants.LOG_CUSTOMER_UPDATE_SUCCESS, updatedCustomer.getId());
        return updatedCustomer;
    }

    /**
     * 更新客戶狀態
     */
    public Customer updateCustomerStatus(Long id, CustomerStatus status) {
        log.info(CustomerConstants.LOG_UPDATE_CUSTOMER_STATUS, id, status);
        
        Customer customer = queryService.getCustomerById(id);
        customer.setStatus(status);
        
        Customer updatedCustomer = customerRepository.save(customer);
        log.info(CustomerConstants.LOG_CUSTOMER_STATUS_UPDATE_SUCCESS, updatedCustomer.getId());
        return updatedCustomer;
    }

    /**
     * 更新客戶欄位
     */
    private void updateCustomerFields(Customer existingCustomer, Customer newCustomer) {
        existingCustomer.setName(newCustomer.getName());
        existingCustomer.setNick(newCustomer.getNick());
        existingCustomer.setGender(newCustomer.getGender());
        existingCustomer.setBirthDate(newCustomer.getBirthDate());
        existingCustomer.setFbAccount(newCustomer.getFbAccount());
        existingCustomer.setLineAccount(newCustomer.getLineAccount());
        existingCustomer.setEmail(newCustomer.getEmail());
        existingCustomer.setPhone(newCustomer.getPhone());
        existingCustomer.setAddress(newCustomer.getAddress());
        existingCustomer.setNote(newCustomer.getNote());
    }
}