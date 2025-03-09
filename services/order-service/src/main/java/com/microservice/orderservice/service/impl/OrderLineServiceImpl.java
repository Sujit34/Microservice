package com.microservice.orderservice.service.impl;

import com.microservice.orderservice.dto.orderLine.OrderLineRequest;
import com.microservice.orderservice.dto.orderLine.OrderLineResponse;
import com.microservice.orderservice.mapper.OrderLineMapper;
import com.microservice.orderservice.repository.OrderLineRepository;
import com.microservice.orderservice.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
