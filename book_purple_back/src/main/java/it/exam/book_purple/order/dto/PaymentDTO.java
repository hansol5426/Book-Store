package it.exam.book_purple.order.dto;

import java.time.LocalDateTime;

import it.exam.book_purple.order.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentDTO {

    private int paymentId;
    private int orderId;
    private String paymentMethod;
    private LocalDateTime paymentTime;

    public static PaymentDTO of(PaymentEntity entity){
        return PaymentDTO.builder()
                         .paymentId(entity.getPaymentId())
                         .orderId(entity.getOrder().getOrderId())
                         .paymentMethod(entity.getPaymentMethod())
                         .paymentTime(entity.getPaidTime())
                         .build();
    }
 
}
