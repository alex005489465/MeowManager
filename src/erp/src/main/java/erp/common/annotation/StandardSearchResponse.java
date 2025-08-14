package erp.common.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 搜索操作的標準響應
 * 適用於搜索和查詢操作，包含成功、參數錯誤和服務器錯誤響應
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "查詢成功"),
    @ApiResponse(responseCode = "400", description = "請求參數錯誤"),
    @ApiResponse(responseCode = "500", description = "內部服務器錯誤")
})
public @interface StandardSearchResponse {
}