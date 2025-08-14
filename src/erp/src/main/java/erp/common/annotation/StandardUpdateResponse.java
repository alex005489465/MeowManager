package erp.common.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 更新操作的標準響應
 * 適用於更新現有資源的操作，包含更新成功、參數錯誤、資源不存在和服務器錯誤響應
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "更新成功"),
    @ApiResponse(responseCode = "400", description = "請求參數錯誤"),
    @ApiResponse(responseCode = "404", description = "資源不存在"),
    @ApiResponse(responseCode = "500", description = "內部服務器錯誤")
})
public @interface StandardUpdateResponse {
}