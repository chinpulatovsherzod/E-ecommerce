package org.example.ecommerce.service;

import org.example.ecommerce.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem addOrderItem(Long orderId, Long productId, Integer quantity);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    OrderItem updateQuantity(Long orderItemId, Integer quantity);

    void deleteOrderItem(Long orderItemId);
}
