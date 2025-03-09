package com.microservice.orderservice.mapper;

import com.microservice.orderservice.dto.orderLine.OrderLineRequest;
import com.microservice.orderservice.dto.orderLine.OrderLineResponse;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.orderId())
                .productId(request.productId())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
