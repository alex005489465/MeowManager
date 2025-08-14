package erp.customer.controller;

import erp.common.controller.BaseController;
import erp.common.dto.ApiResponse;
import erp.common.dto.BasePageResponse;
import erp.common.dto.BasePageableRequest;
import erp.customer.constant.CustomerConstants;
import erp.customer.constant.CustomerApiConstants;
import erp.customer.entity.Customer;
import erp.customer.service.CustomerService;
import erp.customer.mapper.CustomerMapper;
import erp.customer.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import erp.common.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

/**
 * 客戶控制器
 */
@RestController
@RequestMapping(CustomerApiConstants.API_BASE_PATH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "客戶管理", description = "客戶相關的CRUD操作和查詢功能")
public class CustomerController extends BaseController<Customer, Long> {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    //region 基本CRUD操作
    /**
     * 創建客戶
     */
    @Operation(summary = "創建客戶", description = "創建新的客戶資料")
    @StandardCreateResponse
    @PostMapping(CustomerApiConstants.CREATE_PATH)
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
        logRequest("創建客戶", request.getName(), request.getEmail());
        Customer createdCustomer = customerService.createCustomer(request);
        logResponse("創建客戶", createdCustomer.getId());
        return created(CustomerConstants.CUSTOMER_CREATE_SUCCESS, createdCustomer);
    }

    /**
     * 更新客戶
     */
    @Operation(summary = "更新客戶", description = "更新現有客戶的資料")
    @StandardUpdateResponse
    @PostMapping(CustomerApiConstants.UPDATE_PATH)
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(@Valid @RequestBody CustomerUpdateRequest request) {
        logRequest("更新客戶", request.getId(), request.getName());
        // 使用 CustomerMapper 將 DTO 轉換為 Customer 對象
        Customer customer = customerMapper.toEntity(request);
        
        Customer updatedCustomer = customerService.updateCustomer(request.getId(), customer);
        logResponse("更新客戶", updatedCustomer.getId());
        return success(CustomerConstants.CUSTOMER_UPDATE_SUCCESS, updatedCustomer);
    }

    //endregion

    //region 基本查詢操作

    /**
     * 根據ID獲取客戶
     */
    @Operation(summary = "根據ID獲取客戶", description = "通過客戶ID查詢單個客戶資料")
    @StandardResourceResponse
    @PostMapping(CustomerApiConstants.GET_BY_ID_PATH)
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@RequestBody CustomerSearchIdRequest request) {
        Customer customer = customerService.getCustomerById(request.getId());
        return success(customer);
    }

    /**
     * 獲取所有客戶（分頁）
     */
    @Operation(summary = "獲取所有客戶", description = "分頁查詢所有客戶資料")
    @StandardQueryResponse
    @PostMapping(CustomerApiConstants.GET_ALL_PATH)
    public ResponseEntity<ApiResponse<BasePageResponse<Customer>>> getAllCustomers(@RequestBody BasePageableRequest request) {
        Pageable pageable = createPageable(request);
        Page<Customer> customers = customerService.getAllCustomers(pageable);
        return pageSuccess(customers);
    }

    /**
     * 根據手機號碼查找客戶
     */
    @Operation(summary = "根據手機號碼查找客戶", description = "通過手機號碼查詢客戶資料")
    @StandardResourceResponse
    @PostMapping(CustomerApiConstants.GET_BY_PHONE_PATH)
    public ResponseEntity<Customer> getCustomerByPhone(@RequestBody CustomerPhoneRequest request) {
        try {
            Optional<Customer> customer = customerService.getCustomerByPhone(request.getPhone());
            return customer.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error(CustomerConstants.LOG_ERROR_GET_BY_PHONE, request.getPhone(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根據Email查找客戶
     */
    @Operation(summary = "根據Email查找客戶", description = "通過Email查詢客戶資料")
    @StandardResourceResponse
    @PostMapping(CustomerApiConstants.GET_BY_EMAIL_PATH)
    public ResponseEntity<Customer> getCustomerByEmail(@RequestBody CustomerEmailRequest request) {
        try {
            Optional<Customer> customer = customerService.getCustomerByEmail(request.getEmail());
            return customer.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error(CustomerConstants.LOG_ERROR_GET_BY_EMAIL, request.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根據姓名搜索客戶
     */
    @Operation(summary = "根據姓名搜索客戶", description = "通過姓名搜索客戶資料")
    @StandardSearchResponse
    @PostMapping(CustomerApiConstants.SEARCH_BY_NAME_PATH)
    public ResponseEntity<List<Customer>> searchCustomersByName(@RequestBody CustomerNameRequest request) {
        try {
            List<Customer> customers = customerService.searchCustomersByName(request.getName());
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            log.error(CustomerConstants.LOG_ERROR_SEARCH_BY_NAME, request.getName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //endregion

    //region 狀態相關操作
    /**
     * 根據狀態查找客戶
     */
    @Operation(summary = "根據狀態查找客戶", description = "查詢指定狀態的客戶資料")
    @StandardSearchResponse
    @PostMapping(CustomerApiConstants.GET_BY_STATUS_PATH)
    public ResponseEntity<ApiResponse<BasePageResponse<Customer>>> getCustomersByStatus(@RequestBody CustomerSearchStatusRequest request) {
        try {
            Pageable pageable = createPageable(request);
            Page<Customer> customers = customerService.getCustomersByStatus(request.getStatus(), pageable);
            return pageSuccess(customers);
        } catch (Exception e) {
            log.error(CustomerConstants.LOG_ERROR_GET_BY_STATUS, request.getStatus(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("查詢客戶狀態失敗", "SEARCH_ERROR"));
        }
    }

    /**
     * 統計各狀態的客戶數量
     */
    @Operation(summary = "統計各狀態的客戶數量", description = "獲取各狀態客戶的統計信息")
    @StandardQueryResponse
    @PostMapping(CustomerApiConstants.STATUS_STATISTICS_PATH)
    public ResponseEntity<List<Object[]>> getCustomerStatusStatistics() {
        try {
            List<Object[]> statistics = customerService.getCustomerStatusStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            log.error(CustomerConstants.LOG_ERROR_STATUS_STATISTICS, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 更新客戶狀態
     */
    @Operation(summary = "更新客戶狀態", description = "更新客戶的狀態")
    @StandardUpdateResponse
    @PostMapping(CustomerApiConstants.UPDATE_STATUS_PATH)
    public ResponseEntity<ApiResponse<Customer>> updateCustomerStatus(@RequestBody CustomerStatusUpdateRequest request) {
        logRequest("更新客戶狀態", request.getId(), request.getStatus());
        Customer updatedCustomer = customerService.updateCustomerStatus(request.getId(), request.getStatus());
        logResponse("更新客戶狀態", updatedCustomer.getId());
        return success(CustomerConstants.CUSTOMER_STATUS_UPDATE_SUCCESS, updatedCustomer);
    }
    //endregion

    //region 高級搜索操作
    /**
     * 多條件搜索客戶
     */
    @Operation(summary = "多條件搜索客戶", description = "根據多個條件搜索客戶")
    @StandardSearchResponse
    @PostMapping(CustomerApiConstants.SEARCH_PATH)
    public ResponseEntity<ApiResponse<BasePageResponse<Customer>>> searchCustomers(@RequestBody CustomerSearchRequest request) {
        try {
            logRequest("多條件搜索客戶", request.getName(), request.getPhone(), request.getEmail(), request.getStatus());
            Pageable pageable = createPageable(request);
            Page<Customer> customers = customerService.searchCustomers(
                request.getName(), request.getPhone(), request.getEmail(), request.getStatus(), pageable);
            logResponse("搜索客戶", customers.getTotalElements());
            return pageSuccess(customers);
        } catch (Exception e) {
            log.error(CustomerConstants.LOG_ERROR_MULTI_SEARCH, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("搜索客戶失敗", "SEARCH_ERROR"));
        }
    }

    /**
     * 根據出生日期範圍查找客戶
     */
    @Operation(summary = "根據出生日期範圍查找客戶", description = "查詢指定出生日期範圍內的客戶")
    @StandardSearchResponse
    @PostMapping(CustomerApiConstants.GET_BY_BIRTH_DATE_RANGE_PATH)
    public ResponseEntity<List<Customer>> getCustomersByBirthDateRange(@RequestBody CustomerBirthDateRangeRequest request) {
        try {
            List<Customer> customers = customerService.getCustomersByBirthDateRange(request.getStartDate(), request.getEndDate());
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            log.error(CustomerConstants.LOG_ERROR_BIRTH_DATE_RANGE, request.getStartDate(), request.getEndDate(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 獲取最近註冊的客戶
     */
    @Operation(summary = "獲取最近註冊的客戶", description = "獲取最近註冊的客戶列表")
    @StandardQueryResponse
    @PostMapping(CustomerApiConstants.GET_RECENT_PATH)
    public ResponseEntity<List<Customer>> getRecentCustomers() {
        try {
            List<Customer> customers = customerService.getRecentCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            log.error(CustomerConstants.LOG_ERROR_RECENT_CUSTOMERS, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //endregion
}