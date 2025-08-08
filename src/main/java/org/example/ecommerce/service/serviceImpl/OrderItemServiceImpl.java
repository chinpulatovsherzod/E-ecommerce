package org.example.ecommerce.service.serviceImpl;

import jakarta.transaction.Transactional;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.model.OrderItem;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.repository.OrderItemRepository;
import org.example.ecommerce.repository.OrderRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    @Transactional
    public OrderItem addOrderItem(Long orderId, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0){
            throw new IllegalArgumentException("Miqdor noldan katta bo'lishi kerak");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Buyurtma id bilan topilmadi" + orderId));

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("ID orqali bilan mahsulot topilmadi" + productId));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);

        return orderItemRepository.save(orderItem);

    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrder_Id(orderId);
    }

    @Override
    public OrderItem updateQuantity(Long orderItemId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Miqdor noldan katta bo'lishi kerak");
        }

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Buyurtma id bilan topilmadi" + orderItemId));

        orderItem.setQuantity(quantity);

        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
