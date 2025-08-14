package erp.order.enums;

import erp.common.enums.CodedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 訂單狀態枚舉
 */
@Getter
@Schema(description = "訂單狀態", example = "PENDING")
public enum OrderStatus implements CodedEnum {
    DRAFT(0, "草稿"),
    PENDING(1, "已下單"),
    PAID(2, "已付款"),
    SHIPPED(3, "已出貨"),
    COMPLETED(4, "已完成"),
    CANCELLED(5, "已取消");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

}