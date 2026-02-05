package com.example.order_service.Controller;

import com.example.order_service.Feign.Dto.OrderRequest;
import com.example.order_service.Model.Order;
import com.example.order_service.Service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id){
        return service.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest){
        return service.createOrder(orderRequest);
    }
}
