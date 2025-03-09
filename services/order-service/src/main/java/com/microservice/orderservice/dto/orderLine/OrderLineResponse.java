package com.microservice.orderservice.dto.orderLine;

public record OrderLineResponse(
        Integer id,
        double quantity
) { }
