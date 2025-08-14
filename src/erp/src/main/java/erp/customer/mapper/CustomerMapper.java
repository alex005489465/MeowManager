package erp.customer.mapper;

import erp.customer.dto.CustomerCreateRequest;
import erp.customer.dto.CustomerUpdateRequest;
import erp.customer.entity.Customer;
import erp.customer.enums.CustomerStatus;
import org.mapstruct.*;

/**
 * Customer 實體與 DTO 之間的映射器
 * 使用 MapStruct 進行自動映射
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    /**
     * 將 CustomerCreateRequest 轉換為 Customer 實體
     * 忽略 id、status、createdAt、updatedAt 字段
     * 
     * @param request 客戶創建請求
     * @return Customer 實體
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerCreateRequest request);

    /**
     * 將 CustomerUpdateRequest 轉換為 Customer 實體
     * 保留 id，忽略 createdAt、updatedAt 字段
     * 
     * @param request 客戶更新請求
     * @return Customer 實體
     */
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerUpdateRequest request);

    /**
     * 更新現有的 Customer 實體
     * 從 CustomerUpdateRequest 更新字段，但保留 status、createdAt、updatedAt
     * 
     * @param request 客戶更新請求
     * @param target 目標 Customer 實體
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(CustomerUpdateRequest request, @MappingTarget Customer target);

    /**
     * 將 Customer 實體轉換為 CustomerCreateRequest
     * 主要用於測試或特殊場景
     * 
     * @param customer Customer 實體
     * @return CustomerCreateRequest
     */
    CustomerCreateRequest toCreateRequest(Customer customer);

    /**
     * 將 Customer 實體轉換為 CustomerUpdateRequest
     * 主要用於測試或特殊場景
     * 
     * @param customer Customer 實體
     * @return CustomerUpdateRequest
     */
    CustomerUpdateRequest toUpdateRequest(Customer customer);
}