package com.microservice.customerservice.dto;

import com.microservice.customerservice.model.Address;

public record CustomerResponse(String id, String firstname, String lastname,
                               String email, Address address) {
}
