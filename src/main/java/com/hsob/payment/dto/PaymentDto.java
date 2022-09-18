package com.hsob.payment.dto;

import com.hsob.documentdb.payment.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDto {
    private Long id;
    private BigDecimal paymentValue;
    private String userName;
    private String cardNumber;
    private String expirationDate;
    private String securityCode;
    private Status status;
    private Long orderId;
    private Long paymentMethodId;
}
