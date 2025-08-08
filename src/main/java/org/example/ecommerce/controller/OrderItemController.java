package org.example.ecommerce.controller;

import org.apache.coyote.Response;
import org.example.ecommerce.model.OrderItem;
import org.example.ecommerce.service.serviceImpl.OrderItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemServiceImpl orderItemService;

    @PostMapping
    public ResponseEntity<OrderItem> addOrderItem(@RequestParam Long orderId,
                                                  @RequestParam Long productId,
                                                  @RequestParam Integer quantity){
        OrderItem orderItem = orderItemService.addOrderItem(orderId, productId, quantity);
        return ResponseEntity.ok(orderItem);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId){
        List<OrderItem> items = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(items);
    }


    @PutMapping("/{id}/quantity")
    public ResponseEntity<OrderItem> updateQuantity(@PathVariable Long id,
                                                    @RequestParam Integer quantity){
        OrderItem orderItem = orderItemService.updateQuantity(id, quantity);
        return ResponseEntity.ok(orderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderItem> deleteOrderItem(@PathVariable Long id){
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
