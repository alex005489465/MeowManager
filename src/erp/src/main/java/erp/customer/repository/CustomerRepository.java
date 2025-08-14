package erp.customer.repository;

import erp.customer.entity.Customer;
import erp.customer.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 客戶資料存取層
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * 根據姓名查找客戶
     */
    List<Customer> findByNameContainingIgnoreCase(String name);

    /**
     * 根據手機號碼查找客戶
     */
    Optional<Customer> findByPhone(String phone);

    /**
     * 根據Email查找客戶
     */
    Optional<Customer> findByEmail(String email);

    /**
     * 根據狀態查找客戶
     */
    List<Customer> findByStatus(CustomerStatus status);

    /**
     * 根據狀態分頁查找客戶
     */
    Page<Customer> findByStatus(CustomerStatus status, Pageable pageable);

    /**
     * 根據姓名和狀態查找客戶
     */
    List<Customer> findByNameContainingIgnoreCaseAndStatus(String name, CustomerStatus status);

    /**
     * 根據出生日期範圍查找客戶
     */
    List<Customer> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 檢查手機號碼是否已存在
     */
    boolean existsByPhone(String phone);

    /**
     * 檢查Email是否已存在
     */
    boolean existsByEmail(String email);

    /**
     * 根據多個條件搜索客戶（支援模糊查詢）
     */
    @Query("SELECT c FROM Customer c WHERE " +
           "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:phone IS NULL OR c.phone LIKE CONCAT('%', :phone, '%')) AND " +
           "(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:status IS NULL OR c.status = :status)")
    Page<Customer> findByMultipleConditions(
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("status") CustomerStatus status,
            Pageable pageable);

    /**
     * 統計各狀態的客戶數量
     */
    @Query("SELECT c.status, COUNT(c) FROM Customer c GROUP BY c.status")
    List<Object[]> countByStatus();

    /**
     * 查找最近註冊的客戶
     */
    List<Customer> findTop10ByOrderByCreatedAtDesc();

    /**
     * 根據暱稱查找客戶
     */
    List<Customer> findByNickContainingIgnoreCase(String nick);

    /**
     * 查找有Facebook帳號的客戶
     */
    List<Customer> findByFbAccountIsNotNull();

    /**
     * 查找有LINE帳號的客戶
     */
    List<Customer> findByLineAccountIsNotNull();
}