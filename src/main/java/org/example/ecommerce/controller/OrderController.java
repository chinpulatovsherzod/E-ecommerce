package org.example.ecommerce.controller;



import org.example.ecommerce.enums.OrderStatus;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.request.OrderItemRequest;
import org.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam String name,
                                             @RequestParam String email,
                                             @RequestParam Long userId,
                                             @RequestBody List<OrderItemRequest> items){
        Order order = orderService.createOrder(name,email,userId, items);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable Long userId){
        List<Order> orders = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
                                                   @RequestParam OrderStatus status){
        Order updateOrder = orderService.updateOrderStatus(id,status);
        return ResponseEntity.ok(updateOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id){
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }


}
