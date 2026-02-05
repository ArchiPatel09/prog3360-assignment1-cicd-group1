package com.example.order_service.Feign;

import com.example.order_service.Feign.Dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface ProductServiceClient {
    @GetMapping("/api/products/{id}")
    ProductDto getProductById(@PathVariable Long id);
}
