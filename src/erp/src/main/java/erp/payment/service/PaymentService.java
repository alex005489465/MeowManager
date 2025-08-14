package erp.payment.service;

import erp.payment.dto.*;
import erp.payment.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 付款服務統一接口
 * 整合所有付款相關的業務操作
 */
public interface PaymentService {
    
    //region 查詢相關方法
    Page<PaymentResponse> searchPayments(PaymentSearchRequest request, Pageable pageable);
    List<PaymentResponse> getPaymentsByOrderId(Long orderId);
    PaymentStatus getOrderPaymentStatus(Long orderId);
    
    //endregion
    //region 處理相關方法
    PaymentResponse processPayment(PaymentRequest request);
    PaymentResponse cancelPayment(Long orderId);

    //endregion
}