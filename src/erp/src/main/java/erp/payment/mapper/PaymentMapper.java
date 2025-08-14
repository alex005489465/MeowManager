package erp.payment.mapper;

import erp.payment.dto.PaymentResponse;
import erp.payment.entity.OrderPayment;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 付款映射器介面
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper {
    
    /**
     * OrderPayment實體轉換為PaymentResponse
     */
    PaymentResponse toResponse(OrderPayment orderPayment);
    
    /**
     * OrderPayment實體列表轉換為PaymentResponse列表
     */
    List<PaymentResponse> toResponseList(List<OrderPayment> orderPayments);
    
    /**
     * OrderPayment實體分頁轉換為PaymentResponse分頁
     */
    default Page<PaymentResponse> toResponsePage(Page<OrderPayment> orderPaymentPage) {
        return orderPaymentPage.map(this::toResponse);
    }
}