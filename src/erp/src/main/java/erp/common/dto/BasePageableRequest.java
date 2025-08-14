package erp.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 基礎分頁請求DTO
 * 頁碼從1開始，去掉sort功能，按照預設的來
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePageableRequest {
    /**
     * 頁碼，從1開始
     */
    private Integer page = 1;
    
    /**
     * 每頁大小
     */
    private Integer size = 20;
    
    /**
     * 轉換為Spring的Pageable對象
     * 將1-based頁碼轉換為0-based
     * 
     * @return Pageable對象
     */
    public Pageable toPageable() {
        // 確保頁碼至少為1
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        // 確保每頁大小至少為1
        int pageSize = (size != null && size > 0) ? size : 20;
        
        return PageRequest.of(pageNumber, pageSize);
    }
}