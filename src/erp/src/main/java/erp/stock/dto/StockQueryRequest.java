package erp.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 庫存查詢請求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "庫存查詢請求")
public class StockQueryRequest {

    @Min(value = 0, message = "頁碼不能為負數")
    @Schema(description = "頁碼", example = "0")
    private Integer page = 0;

    @Min(value = 1, message = "每頁大小必須大於0")
    @Schema(description = "每頁大小", example = "20")
    private Integer size = 20;

    @Schema(description = "排序字段", example = "updatedAt")
    private String sort = "updatedAt";

    @Schema(description = "排序方向", example = "DESC", allowableValues = {"ASC", "DESC"})
    private String direction = "DESC";
}