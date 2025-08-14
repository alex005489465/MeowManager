package erp.customer.service.CustomerServiceImpl;

import erp.customer.dto.CustomerCreateRequest;
import erp.customer.entity.Customer;
import erp.customer.enums.CustomerStatus;
import erp.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 客戶服務主實現類
 * 協調各個功能模塊的實現
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceMainImpl implements CustomerService {
    
    private final CustomerQueryServiceImpl queryService;
    private final CustomerValidationServiceImpl validationService;
    private final CustomerCreateUpdateServiceImpl createUpdateService;
    
    //region 查詢方法委派
    @Override
    public Customer getCustomerById(Long id) {
        return queryService.getCustomerById(id);
    }
    
    @Override
    public Optional<Customer> getCustomerByPhone(String phone) {
        return queryService.getCustomerByPhone(phone);
    }
    
    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return queryService.getCustomerByEmail(email);
    }
    
    @Override
    public List<Customer> searchCustomersByName(String name) {
        return queryService.searchCustomersByName(name);
    }
    
    @Override
    public List<Customer> getCustomersByStatus(CustomerStatus status) {
        return queryService.getCustomersByStatus(status);
    }
    
    @Override
    public Page<Customer> getCustomersByStatus(CustomerStatus status, Pageable pageable) {
        return queryService.getCustomersByStatus(status, pageable);
    }
    
    @Override
    public Page<Customer> searchCustomers(String name, String phone, String email, CustomerStatus status, Pageable pageable) {
        return queryService.searchCustomers(name, phone, email, status, pageable);
    }
    
    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return queryService.getAllCustomers(pageable);
    }
    
    @Override
    public List<Customer> getCustomersByBirthDateRange(LocalDate startDate, LocalDate endDate) {
        return queryService.getCustomersByBirthDateRange(startDate, endDate);
    }
    
    @Override
    public List<Customer> getRecentCustomers() {
        return queryService.getRecentCustomers();
    }
    
    @Override
    public List<Object[]> getCustomerStatusStatistics() {
        return queryService.getCustomerStatusStatistics();
    }
    
    //endregion
    //region 創建更新方法委派
    @Override
    public Customer createCustomer(CustomerCreateRequest request) {
        return createUpdateService.createCustomer(request);
    }
    
    @Override
    public Customer createCustomer(Customer customer) {
        return createUpdateService.createCustomer(customer);
    }
    
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        return createUpdateService.updateCustomer(id, customer);
    }
    
    @Override
    public Customer updateCustomerStatus(Long id, CustomerStatus status) {
        return createUpdateService.updateCustomerStatus(id, status);
    }
    
    //endregion
    //region 驗證方法委派
    @Override
    public void validateCustomer(Customer customer) {
        validationService.validateCustomer(customer);
    }
    
    @Override
    public void validatePhoneFormat(String phone) {
        validationService.validatePhoneFormat(phone);
    }
    
    @Override
    public String normalizePhoneNumber(String phone) {
        return validationService.normalizePhoneNumber(phone);
    }
    
    @Override
    public void validateAddressFormat(String address) {
        validationService.validateAddressFormat(address);
    }
    
    @Override
    public void validatePhoneUniqueness(String phone, Long excludeCustomerId) {
        validationService.validatePhoneUniqueness(phone, excludeCustomerId);
    }
    
    @Override
    public void validateEmailUniqueness(String email, Long excludeCustomerId) {
        validationService.validateEmailUniqueness(email, excludeCustomerId);
    }
    
    @Override
    public void validateCustomerExists(Long customerId) {
        validationService.validateCustomerExists(customerId);
    }

    //endregion
}
