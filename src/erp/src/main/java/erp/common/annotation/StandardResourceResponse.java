package erp.common.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查詢單一資源的標準響應（包含404）
 * 適用於根據ID或特定條件查詢單個資源的操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "查詢成功"),
    @ApiResponse(responseCode = "404", description = "資源不存在"),
    @ApiResponse(responseCode = "500", description = "內部服務器錯誤")
})
public @interface StandardResourceResponse {
}