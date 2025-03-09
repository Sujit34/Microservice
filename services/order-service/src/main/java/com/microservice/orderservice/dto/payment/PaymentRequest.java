package com.microservice.orderservice.dto.payment;

import com.microservice.orderservice.dto.customer.CustomerResponse;
import com.microservice.orderservice.util.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}