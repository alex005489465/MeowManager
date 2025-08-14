package erp.order.dto;

import erp.order.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "訂單狀態更新DTO")
public class OrderStatusUpdateDto {
    
    @Schema(description = "訂單ID", example = "1")
    @NotNull(message = "訂單ID不能為空")
    private Long id;
    
    @Schema(description = "新的訂單狀態", example = "CONFIRMED")
    @NotNull(message = "訂單狀態不能為空")
    private OrderStatus status;
    
    @Schema(description = "狀態變更備註", example = "客戶已確認訂單")
    private String reason;
}