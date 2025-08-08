package org.example.ecommerce.service.serviceImpl;

import org.example.ecommerce.enums.OrderStatus;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.model.OrderItem;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.repository.OrderItemRepository;
import org.example.ecommerce.repository.OrderRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.request.OrderItemRequest;
import org.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(String name, String email, Long id, List<OrderItemRequest> items) {
       if (name == null || email.isBlank() || email==null || name.isBlank()){
           throw new RuntimeException("Ism va email to'ldirilgan bo'lishi kerak!");
       }

       if (!email.matches("Foydalanuvchining pochtasi")){
           throw new RuntimeException("Email notog'ri");
       }
        BigDecimal totalPrice = BigDecimal.ZERO;
       List<OrderItem> orderItems = new ArrayList<>();

       for (OrderItemRequest itemRequest: items){
           Product product = productRepository.findById(itemRequest.getProductId())
           .orElseThrow(()-> new RuntimeException("Maxsulot topilmadi" + itemRequest.getProductId()));

           if (product.getStock() < itemRequest.getQuantity()){
               throw new RuntimeException("Maxsulot etarli emas:" + product.getName());
           }

           BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
           OrderItem orderItem = new OrderItem();
           orderItem.setProduct(product);
           orderItem.setQuantity(itemRequest.getQuantity());
           orderItems.add(orderItem);
       }

       for (OrderItem orderItem: orderItems){
           Product product = orderItem.getProduct();
           product.setStock(product.getStock() - orderItem.getQuantity());
           productRepository.save(product);
       }
        Order order = new Order();
       order.setCustomerName(name);
       order.setCustomerEmail(email);
       order.setUserId(order.getUserId());
       order.setOrderDate(LocalDateTime.now());
       order.setOrderStatus(OrderStatus.PENDING);

       Order savedOrder = orderRepository.save(order);
       for (OrderItem orderItem: orderItems){
           orderItem.setOrder(savedOrder);
           orderItemRepository.save(orderItem);
       }
       return savedOrder;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Order id orqali topilmadi" + id));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrderByUserId(Long id) {
        return orderRepository.findByUserId(id);
    }

    @Override
    public Order updateOrderStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Buyurtma id orqali topilmadi" + id));
        order.setOrderStatus(orderStatus);

        if (order.getOrderStatus() != OrderStatus.PENDING){
            throw new RuntimeException("Faqat PENDING holatidagi buyurtmalarni o'zgartirish mumkin");
        }
        if (orderStatus == OrderStatus.CANCELLED){
            List<OrderItem> orderItems = orderItemRepository.findByOrder_Id(id);
            for (OrderItem orderItem: orderItems){
                Product product = orderItem.getProduct();
                product.setStock(product.getStock() + orderItem.getQuantity());
                productRepository.save(product);
            }
        }
        order.setOrderStatus(orderStatus);

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);

    }
}
