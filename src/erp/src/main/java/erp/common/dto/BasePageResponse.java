package erp.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 基礎分頁響應DTO
 * 解決頁碼1-based轉換問題，並簡化不必要的字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePageResponse<T> {
    
    /**
     * 實際數據列表
     */
    private List<T> content;
    
    /**
     * 當前頁碼（1-based，給前端使用）
     */
    private int currentPage;
    
    /**
     * 每頁大小
     */
    private int pageSize;
    
    /**
     * 總記錄數
     */
    private long totalElements;
    
    /**
     * 總頁數
     */
    private int totalPages;
    
    /**
     * 是否第一頁
     */
    private boolean first;
    
    /**
     * 是否最後一頁
     */
    private boolean last;
    
    /**
     * 當前頁實際元素數量
     */
    private int numberOfElements;
    
    /**
     * 是否為空
     */
    private boolean empty;
    
    /**
     * 從Spring Page對象轉換為基礎分頁響應
     * 自動處理0-based到1-based的頁碼轉換
     */
    public static <T> BasePageResponse<T> of(Page<T> page) {
        BasePageResponse<T> response = new BasePageResponse<>();
        response.setContent(page.getContent());
        response.setCurrentPage(page.getNumber() + 1); // 0-based 轉 1-based
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());
        response.setNumberOfElements(page.getNumberOfElements());
        response.setEmpty(page.isEmpty());
        return response;
    }
}