package com.example.product_service.Controller;

import com.example.product_service.Model.Product;
import com.example.product_service.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // getting all products
    @GetMapping
    public List<Product> getAllProducts() {
        return  service.getAllProducts();
    }

    // getting product by id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // posting (adding) product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return service.createProduct(product);
    }

    // deleting the product
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}
