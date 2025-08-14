package erp.stock.mapper;

import erp.stock.dto.StockResponse;
import erp.stock.entity.Stock;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Stock 實體與 DTO 之間的映射器
 * 使用 MapStruct 進行自動映射
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StockMapper {

    /**
     * 將 Stock 實體轉換為 StockResponse
     */
    StockResponse toResponse(Stock stock);

    /**
     * 將 Stock 實體清單轉換為 StockResponse 清單
     */
    List<StockResponse> toResponseList(List<Stock> stocks);

    /**
     * 將 Stock 實體分頁轉換為 StockResponse 分頁
     */
    default Page<StockResponse> toResponsePage(Page<Stock> stockPage) {
        return stockPage.map(this::toResponse);
    }

    /**
     * 建立新的 Stock 實體（用於初始化）
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "qty", constant = "0")
    @Mapping(target = "avgCost", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "totalCost", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Stock createEntity(Long productId);

    /**
     * 更新現有的 Stock 實體
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateStockValues(Integer qty, java.math.BigDecimal avgCost, 
                          java.math.BigDecimal totalCost, @MappingTarget Stock target);

    /**
     * 複製 Stock 實體（用於備份或快照）
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Stock copyEntity(Stock source);
}