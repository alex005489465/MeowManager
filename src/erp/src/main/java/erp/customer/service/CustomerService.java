package erp.customer.service;

import erp.customer.dto.CustomerCreateRequest;
import erp.customer.entity.Customer;
import erp.customer.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 客戶服務統一接口
 * 整合所有客戶相關的業務操作
 */
public interface CustomerService {
    
    //region 查詢相關方法
    Customer getCustomerById(Long id);
    Optional<Customer> getCustomerByPhone(String phone);
    Optional<Customer> getCustomerByEmail(String email);
    List<Customer> searchCustomersByName(String name);
    List<Customer> getCustomersByStatus(CustomerStatus status);
    Page<Customer> getCustomersByStatus(CustomerStatus status, Pageable pageable);
    Page<Customer> searchCustomers(String name, String phone, String email, CustomerStatus status, Pageable pageable);
    Page<Customer> getAllCustomers(Pageable pageable);
    List<Customer> getCustomersByBirthDateRange(LocalDate startDate, LocalDate endDate);
    List<Customer> getRecentCustomers();
    List<Object[]> getCustomerStatusStatistics();
    //endregion
    
    //region 創建更新相關方法
    Customer createCustomer(CustomerCreateRequest request);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    Customer updateCustomerStatus(Long id, CustomerStatus status);
    //endregion
    
    //region 驗證相關方法
    void validateCustomer(Customer customer);
    void validatePhoneFormat(String phone);
    String normalizePhoneNumber(String phone);
    void validateAddressFormat(String address);
    void validatePhoneUniqueness(String phone, Long excludeCustomerId);
    void validateEmailUniqueness(String email, Long excludeCustomerId);
    void validateCustomerExists(Long customerId);
    //endregion
}
