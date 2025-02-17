package com.microservice.customerservice.service;

import com.microservice.customerservice.dto.CustomerRequest;
import com.microservice.customerservice.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    public String createCustomer(CustomerRequest request);

    public void updateCustomer(CustomerRequest request);

    public List<CustomerResponse> findAllCustomers();

    public CustomerResponse findById(String id);

    public boolean existsById(String id);

    public void deleteCustomer(String id);
}
