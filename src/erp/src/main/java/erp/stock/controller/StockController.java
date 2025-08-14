package erp.stock.controller;

import erp.stock.dto.*;
import erp.stock.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Validated
@Tag(name = "庫存管理", description = "庫存相關API")
public class StockController {

    private final StockService stockService;

    /**
     * 庫存入庫操作 (控制器業務用)
     */
    @PostMapping("/inbound")
    @Operation(summary = "庫存入庫操作", description = "執行商品入庫，自動創建或更新庫存記錄")
    public ResponseEntity<StockMovementResponse> processStockInbound(
            @RequestBody @Valid StockInboundRequest request) {
        
        StockMovementResponse response = stockService.processStockInbound(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 庫存出庫操作 (控制器業務用)
     */
    @PostMapping("/outbound")
    @Operation(summary = "庫存出庫操作", description = "執行商品出庫，自動更新庫存記錄")
    public ResponseEntity<StockMovementResponse> processStockOutbound(
            @RequestBody @Valid StockOutboundRequest request) {
        
        StockMovementResponse response = stockService.processStockOutbound(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 檢查庫存可用性 (控制器業務用)
     */
    @PostMapping("/availability")
    @Operation(summary = "檢查庫存可用性", description = "檢查指定商品的庫存是否充足")
    public ResponseEntity<StockAvailabilityResponse> checkStockAvailability(
            @RequestBody @Valid StockAvailabilityRequest request) {
        
        StockAvailabilityResponse response = stockService.checkStockAvailability(request.getProductId(), request.getRequiredQty());
        return ResponseEntity.ok(response);
    }

    /**
     * 查詢所有庫存
     */
    @PostMapping("/query")
    @Operation(summary = "查詢所有庫存", description = "分頁查詢所有庫存記錄")
    public ResponseEntity<Page<StockResponse>> getAllStocks(
            @RequestBody @Valid StockQueryRequest request) {
        
        Pageable pageable = PageRequest.of(
            request.getPage(), 
            request.getSize(), 
            Sort.Direction.fromString(request.getDirection()), 
            request.getSort()
        );
        Page<StockResponse> stocks = stockService.getAllStocks(pageable);
        return ResponseEntity.ok(stocks);
    }

    /**
     * 根據ID查詢庫存
     */
    @PostMapping("/by-id")
    @Operation(summary = "根據ID查詢庫存", description = "根據庫存ID查詢單筆庫存記錄")
    public ResponseEntity<StockResponse> getStockById(@RequestBody @Valid StockByIdRequest request) {
        
        StockResponse stock = stockService.getStockByProductId(request.getProductId());
        return ResponseEntity.ok(stock);
    }
}