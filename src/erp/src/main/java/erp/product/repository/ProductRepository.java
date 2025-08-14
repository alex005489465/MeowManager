package erp.product.repository;

import erp.product.entity.Product;
import erp.product.enums.ProductType;
import erp.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 商品資料存取層
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 根據商品名稱查找商品
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * 根據商品名稱分頁查找商品
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * 根據商品名稱精確查找商品
     */
    Optional<Product> findByName(String name);

    /**
     * 根據商品類型查找商品
     */
    List<Product> findByType(ProductType type);

    /**
     * 根據商品類型分頁查找商品
     */
    Page<Product> findByType(ProductType type, Pageable pageable);

    /**
     * 根據商品狀態查找商品
     */
    List<Product> findByStatus(ProductStatus status);

    /**
     * 根據商品狀態分頁查找商品
     */
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    /**
     * 根據商品類型和狀態查找商品
     */
    List<Product> findByTypeAndStatus(ProductType type, ProductStatus status);

    /**
     * 根據商品類型和狀態分頁查找商品
     */
    Page<Product> findByTypeAndStatus(ProductType type, ProductStatus status, Pageable pageable);

    /**
     * 根據價格範圍查找商品
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * 根據價格範圍分頁查找商品
     */
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * 查找啟用狀態的商品
     */
    List<Product> findByStatusOrderByNameAsc(ProductStatus status);

    /**
     * 根據商品描述模糊查找商品
     */
    List<Product> findByDescriptionContainingIgnoreCase(String description);

    /**
     * 檢查商品名稱是否已存在
     */
    boolean existsByName(String name);

    /**
     * 檢查商品名稱是否已存在（排除指定ID）
     */
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * 根據多個條件搜索商品
     */
    @Query("SELECT p FROM Product p WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:type IS NULL OR p.type = :type) AND " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    Page<Product> findByMultipleConditions(
            @Param("name") String name,
            @Param("type") ProductType type,
            @Param("status") ProductStatus status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("description") String description,
            Pageable pageable);

    /**
     * 統計各商品類型的商品數量
     */
    @Query("SELECT p.type, COUNT(p) FROM Product p GROUP BY p.type")
    List<Object[]> countByType();

    /**
     * 統計各商品狀態的商品數量
     */
    @Query("SELECT p.status, COUNT(p) FROM Product p GROUP BY p.status")
    List<Object[]> countByStatus();

    /**
     * 統計各商品類型和狀態的商品數量
     */
    @Query("SELECT p.type, p.status, COUNT(p) FROM Product p GROUP BY p.type, p.status")
    List<Object[]> countByTypeAndStatus();

    /**
     * 查找價格最高的商品
     */
    List<Product> findTop10ByOrderByPriceDesc();

    /**
     * 查找價格最低的商品
     */
    List<Product> findTop10ByOrderByPriceAsc();

    /**
     * 查找最新添加的商品
     */
    List<Product> findTop10ByOrderByCreatedAtDesc();

    /**
     * 根據商品類型統計平均價格
     */
    @Query("SELECT p.type, AVG(p.price) FROM Product p GROUP BY p.type")
    List<Object[]> avgPriceByType();

    /**
     * 根據商品類型統計最高價格
     */
    @Query("SELECT p.type, MAX(p.price) FROM Product p GROUP BY p.type")
    List<Object[]> maxPriceByType();

    /**
     * 根據商品類型統計最低價格
     */
    @Query("SELECT p.type, MIN(p.price) FROM Product p GROUP BY p.type")
    List<Object[]> minPriceByType();

    /**
     * 查找指定價格以上的商品
     */
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);

    /**
     * 查找指定價格以下的商品
     */
    List<Product> findByPriceLessThanEqual(BigDecimal price);

    /**
     * 根據商品類型查找啟用狀態的商品（按名稱排序）
     */
    List<Product> findByTypeAndStatusOrderByNameAsc(ProductType type, ProductStatus status);

    /**
     * 根據商品類型查找啟用狀態的商品（按價格排序）
     */
    List<Product> findByTypeAndStatusOrderByPriceAsc(ProductType type, ProductStatus status);

    /**
     * 搜索商品名稱或描述包含關鍵字的商品
     */
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 搜索商品名稱或描述包含關鍵字的商品（分頁）
     */
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 查找有備註的商品
     */
    List<Product> findByNotesIsNotNull();

    /**
     * 查找沒有備註的商品
     */
    List<Product> findByNotesIsNull();
}