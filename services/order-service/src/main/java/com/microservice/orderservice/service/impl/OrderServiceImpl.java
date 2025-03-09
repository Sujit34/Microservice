package com.microservice.orderservice.service.impl;


import com.microservice.orderservice.client.CustomerClient;
import com.microservice.orderservice.client.PaymentClient;
import com.microservice.orderservice.client.ProductClient;
import com.microservice.orderservice.dto.order.OrderRequest;
import com.microservice.orderservice.dto.order.OrderResponse;
import com.microservice.orderservice.dto.orderLine.OrderLineRequest;
import com.microservice.orderservice.dto.payment.PaymentRequest;
import com.microservice.orderservice.dto.product.PurchaseRequest;
import com.microservice.orderservice.exception.BusinessException;
import com.microservice.orderservice.kafka.OrderConfirmation;
import com.microservice.orderservice.kafka.OrderProducer;
import com.microservice.orderservice.mapper.OrderMapper;
import com.microservice.orderservice.repository.OrderRepository;
import com.microservice.orderservice.service.OrderLineService;
import com.microservice.orderservice.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer " +
                        "exists with the provided ID"));

        var purchasedProducts = productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No order found with the provided ID: %d", id)));
    }
}