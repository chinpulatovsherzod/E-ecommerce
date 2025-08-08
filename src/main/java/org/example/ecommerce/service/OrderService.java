package org.example.ecommerce.service;

import org.example.ecommerce.enums.OrderStatus;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.request.OrderItemRequest;

import java.util.List;

public interface OrderService {

    Order getOrderById(Long id);

    List<Order> getAllOrders();

    List<Order> getOrderByUserId(Long id);

    Order updateOrderStatus(Long id, OrderStatus orderStatus);

    void deleteOrderById(Long id);

    Order createOrder(String name, String email, Long userId, List<OrderItemRequest> items);
}
