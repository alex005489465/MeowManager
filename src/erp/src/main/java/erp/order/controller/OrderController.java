package erp.order.controller;

import erp.common.annotation.StandardCreateResponse;
import erp.common.dto.ApiResponse;
import erp.order.constant.OrderConstants;
import erp.order.dto.OrderCreateRequestDto;
import erp.order.dto.OrderCreateResponseDto;
import erp.order.dto.OrderUpdateRequestDto;
import erp.order.dto.OrderUpdateResponseDto;
import erp.order.dto.OrderStatusUpdateDto;
import erp.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "訂單管理", description = "訂單相關API")
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    @Operation(summary = "創建訂單", description = "創建新訂單")
    @StandardCreateResponse
    public ResponseEntity<ApiResponse<OrderCreateResponseDto>> createOrder(
            @Valid @RequestBody OrderCreateRequestDto request) {
        
        OrderCreateResponseDto response = orderService.createOrder(request);
        
        return ResponseEntity.ok(ApiResponse.success(
            OrderConstants.ORDER_CREATE_SUCCESS, 
            response
        ));
    }
    
    /**
     * 更新訂單
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新訂單", description = "更新現有訂單的詳細資訊")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "更新成功"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "請求參數錯誤"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "訂單不存在"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "訂單狀態不允許修改")
    })
    public ResponseEntity<ApiResponse<OrderUpdateResponseDto>> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderUpdateRequestDto request) {
        
        // 確保路徑參數與請求體中的ID一致
        request.setId(id);
        
        OrderUpdateResponseDto response = orderService.updateOrder(request);
        
        return ResponseEntity.ok(ApiResponse.success(
            OrderConstants.ORDER_UPDATE_SUCCESS, 
            response
        ));
    }

    /**
     * 更新訂單狀態
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "更新訂單狀態", description = "僅更新訂單狀態")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "狀態更新成功"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "狀態變更不合法"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "訂單不存在")
    })
    public ResponseEntity<ApiResponse<OrderUpdateResponseDto>> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateDto request) {
        
        request.setId(id);
        
        OrderUpdateResponseDto response = orderService.updateOrderStatus(request);
        
        return ResponseEntity.ok(ApiResponse.success(
            OrderConstants.ORDER_STATUS_UPDATE_SUCCESS, 
            response
        ));
    }
}