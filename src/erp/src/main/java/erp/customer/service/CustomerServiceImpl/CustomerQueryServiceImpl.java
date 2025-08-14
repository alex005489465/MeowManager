package erp.customer.service.CustomerServiceImpl;

import erp.common.constant.ErrorCode;
import erp.common.exception.CustomerException;
import erp.customer.constant.CustomerConstants;
import erp.customer.entity.Customer;
import erp.customer.enums.CustomerStatus;
import erp.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 客戶查詢功能實現
 * 從原 CustomerQueryService 遷移而來
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomerQueryServiceImpl {

    private final CustomerRepository customerRepository;

    /**
     * 根據ID查找客戶
     */
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException(CustomerConstants.CUSTOMER_NOT_FOUND + id, ErrorCode.CUSTOMER_NOT_FOUND));
    }

    /**
     * 根據手機號碼查找客戶
     */
    public Optional<Customer> getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    /**
     * 根據Email查找客戶
     */
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    /**
     * 根據姓名搜索客戶
     */
    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * 根據狀態查找客戶
     */
    public List<Customer> getCustomersByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }

    /**
     * 根據狀態分頁查找客戶
     */
    public Page<Customer> getCustomersByStatus(CustomerStatus status, Pageable pageable) {
        return customerRepository.findByStatus(status, pageable);
    }

    /**
     * 多條件搜索客戶
     */
    public Page<Customer> searchCustomers(String name, String phone, String email, 
                                        CustomerStatus status, Pageable pageable) {
        return customerRepository.findByMultipleConditions(name, phone, email, status, pageable);
    }

    /**
     * 獲取所有客戶（分頁）
     */
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    /**
     * 根據出生日期範圍查找客戶
     */
    public List<Customer> getCustomersByBirthDateRange(LocalDate startDate, LocalDate endDate) {
        return customerRepository.findByBirthDateBetween(startDate, endDate);
    }

    /**
     * 獲取最近註冊的客戶
     */
    public List<Customer> getRecentCustomers() {
        return customerRepository.findTop10ByOrderByCreatedAtDesc();
    }

    /**
     * 統計各狀態的客戶數量
     */
    public List<Object[]> getCustomerStatusStatistics() {
        return customerRepository.countByStatus();
    }
}