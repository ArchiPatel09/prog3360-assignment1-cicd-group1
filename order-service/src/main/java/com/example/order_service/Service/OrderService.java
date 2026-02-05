package com.example.order_service.Service;

import com.example.order_service.Feign.Dto.OrderRequest;
import com.example.order_service.Feign.Dto.ProductDto;
import com.example.order_service.Feign.ProductServiceClient;
import com.example.order_service.Model.Order;
import com.example.order_service.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final ProductServiceClient productServiceClient;

    public OrderService(OrderRepository repository, ProductServiceClient productServiceClient) {
        this.repository = repository;
        this.productServiceClient = productServiceClient;
    }

    public List<Order> getAllOrders(){
        return repository.findAll();
    }

    public Order getOrderById(Long id){
        return repository.findById(id).orElse(null);
    }

    public Order createOrder(OrderRequest orderRequest){
        ProductDto product = productServiceClient.getProductById(orderRequest.getProductId());

        if(product == null){
            throw new RuntimeException("Product not found");
        }

        if(product.getQuantity() < orderRequest.getQuantity()){
            throw new RuntimeException("Not enough stock available!");
        }

        double totalPrice = product.getPrice() * orderRequest.getQuantity();

        Order order = new Order(
                orderRequest.getProductId(),
                orderRequest.getQuantity(),
                totalPrice,
                "PENDING"
        );

        return repository.save(order);
    }
}
