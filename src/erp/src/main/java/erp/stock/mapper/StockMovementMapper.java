package erp.stock.mapper;

import erp.stock.dto.StockMovementCreateRequest;
import erp.stock.dto.StockMovementResponse;
import erp.stock.entity.StockMovement;
import erp.stock.enums.MovementType;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * StockMovement 實體與 DTO 之間的映射器
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StockMovementMapper {

    /**
     * 將 StockMovement 實體轉換為 StockMovementResponse
     */
    StockMovementResponse toResponse(StockMovement stockMovement);

    /**
     * 將 StockMovement 實體清單轉換為 StockMovementResponse 清單
     */
    List<StockMovementResponse> toResponseList(List<StockMovement> stockMovements);

    /**
     * 將 StockMovement 實體分頁轉換為 StockMovementResponse 分頁
     */
    default Page<StockMovementResponse> toResponsePage(Page<StockMovement> stockMovementPage) {
        return stockMovementPage.map(this::toResponse);
    }

    /**
     * 將 StockMovementCreateRequest 轉換為 StockMovement 實體
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalCost", ignore = true) // 由 @PrePersist 自動計算
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StockMovement toEntity(StockMovementCreateRequest request);

    /**
     * 建立庫存變動記錄實體
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stockId", source = "stockId")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "movementType", source = "movementType")
    @Mapping(target = "qty", source = "qty")
    @Mapping(target = "unitCost", source = "unitCost")
    @Mapping(target = "totalCost", ignore = true) // 由 @PrePersist 自動計算
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StockMovement createMovementEntity(Long stockId, Long productId, MovementType movementType, 
                                     Integer qty, BigDecimal unitCost, String reason);

    /**
     * 建立庫存變動記錄實體（不帶原因）
     */
    default StockMovement createMovementEntity(Long stockId, Long productId, MovementType movementType, 
                                             Integer qty, BigDecimal unitCost) {
        return createMovementEntity(stockId, productId, movementType, qty, unitCost, null);
    }

    /**
     * 複製 StockMovement 實體（用於備份或稽核）
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StockMovement copyEntity(StockMovement source);
}