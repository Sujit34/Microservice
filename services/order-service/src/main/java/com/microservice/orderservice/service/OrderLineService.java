package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.orderLine.OrderLineRequest;
import com.microservice.orderservice.dto.orderLine.OrderLineResponse;

import java.util.List;

public interface OrderLineService {
    public Integer saveOrderLine(OrderLineRequest request);
    public List<OrderLineResponse> findAllByOrderId(Integer orderId);
}
