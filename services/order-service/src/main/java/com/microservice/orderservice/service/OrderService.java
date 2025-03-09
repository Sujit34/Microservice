package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.order.OrderRequest;
import com.microservice.orderservice.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {

    public Integer createOrder(OrderRequest request);
    public List<OrderResponse> findAllOrders();
    public OrderResponse findById(Integer id);
}
