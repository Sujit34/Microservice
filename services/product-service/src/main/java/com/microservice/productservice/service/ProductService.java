package com.microservice.productservice.service;

import com.microservice.productservice.dto.ProductPurchaseRequest;
import com.microservice.productservice.dto.ProductPurchaseResponse;
import com.microservice.productservice.dto.ProductRequest;
import com.microservice.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    public Integer createProduct(ProductRequest request);
    public ProductResponse findById(Integer id);
    public List<ProductResponse> findAll();
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request);

}
