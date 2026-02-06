package com.example.order_service.Service;

import com.example.order_service.Feign.Dto.OrderRequest;
import com.example.order_service.Feign.Dto.ProductDto;
import com.example.order_service.Feign.ProductServiceClient;
import com.example.order_service.Model.Order;
import com.example.order_service.Repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    // repository for oder data persistence
    private final OrderRepository repository;

    // client to call product-service
    private final ProductServiceClient productServiceClient;

    public OrderService(OrderRepository repository, ProductServiceClient productServiceClient) {
        this.repository = repository;
        this.productServiceClient = productServiceClient;
    }

    // retrieving all orders from the database
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    // finding order by ID, return null if not found
    public Order getOrderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // fetching product details from product-service using feign client
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        ProductDto product = productServiceClient.getProductById(orderRequest.getProductId());

        // validate product existence
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // checking stock availability
        if (product.getQuantity() < orderRequest.getQuantity()) {
            throw new RuntimeException("Not enough stock available!");
        }

        // calculating order total
        double calculatedTotalPrice = product.getPrice() * orderRequest.getQuantity();

        // creating and saving new order
        Order order = new Order(
                orderRequest.getProductId(),
                orderRequest.getQuantity(),
                calculatedTotalPrice,
                orderRequest.getStatus()
        );

        return repository.save(order);
    }
}