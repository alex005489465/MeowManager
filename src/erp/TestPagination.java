import erp.common.dto.BasePageableRequest;
import erp.customer.dto.CustomerSearchStatusRequest;
import erp.customer.dto.CustomerSearchRequest;
import erp.customer.enums.CustomerStatus;
import org.springframework.data.domain.Pageable;

/**
 * 測試分頁功能
 */
public class TestPagination {
    public static void main(String[] args) {
        System.out.println("[DEBUG_LOG] 開始測試分頁功能");
        
        // 測試BasePageableRequest
        System.out.println("[DEBUG_LOG] 測試BasePageableRequest:");
        BasePageableRequest baseRequest = new BasePageableRequest();
        System.out.println("[DEBUG_LOG] 預設頁碼: " + baseRequest.getPage()); // 應該是1
        System.out.println("[DEBUG_LOG] 預設大小: " + baseRequest.getSize()); // 應該是20
        
        Pageable pageable = baseRequest.toPageable();
        System.out.println("[DEBUG_LOG] 轉換後的頁碼(0-based): " + pageable.getPageNumber()); // 應該是0
        System.out.println("[DEBUG_LOG] 轉換後的大小: " + pageable.getPageSize()); // 應該是20
        
        // 測試頁碼為3的情況
        BasePageableRequest pageRequest = new BasePageableRequest(3, 10);
        Pageable pageable3 = pageRequest.toPageable();
        System.out.println("[DEBUG_LOG] 頁碼3轉換後(0-based): " + pageable3.getPageNumber()); // 應該是2
        
        // 測試BasePageableRequest (原CustomerPageableRequest)
        System.out.println("[DEBUG_LOG] 測試BasePageableRequest (原CustomerPageableRequest):");
        BasePageableRequest customerRequest = new BasePageableRequest();
        Pageable customerPageable = customerRequest.toPageable();
        System.out.println("[DEBUG_LOG] BasePageableRequest頁碼: " + customerRequest.getPage());
        System.out.println("[DEBUG_LOG] BasePageableRequest轉換後頁碼: " + customerPageable.getPageNumber());
        
        // 測試CustomerSearchStatusRequest
        System.out.println("[DEBUG_LOG] 測試CustomerSearchStatusRequest:");
        CustomerSearchStatusRequest statusRequest = new CustomerSearchStatusRequest();
        statusRequest.setPage(2);
        statusRequest.setSize(15);
        statusRequest.setStatus(CustomerStatus.ACTIVE);
        Pageable statusPageable = statusRequest.toPageable();
        System.out.println("[DEBUG_LOG] StatusRequest頁碼: " + statusRequest.getPage());
        System.out.println("[DEBUG_LOG] StatusRequest轉換後頁碼: " + statusPageable.getPageNumber());
        System.out.println("[DEBUG_LOG] StatusRequest狀態: " + statusRequest.getStatus());
        
        // 測試CustomerSearchRequest
        System.out.println("[DEBUG_LOG] 測試CustomerSearchRequest:");
        CustomerSearchRequest searchRequest = new CustomerSearchRequest();
        searchRequest.setPage(5);
        searchRequest.setSize(25);
        searchRequest.setName("測試");
        Pageable searchPageable = searchRequest.toPageable();
        System.out.println("[DEBUG_LOG] SearchRequest頁碼: " + searchRequest.getPage());
        System.out.println("[DEBUG_LOG] SearchRequest轉換後頁碼: " + searchPageable.getPageNumber());
        System.out.println("[DEBUG_LOG] SearchRequest姓名: " + searchRequest.getName());
        
        System.out.println("[DEBUG_LOG] 分頁功能測試完成");
    }
}