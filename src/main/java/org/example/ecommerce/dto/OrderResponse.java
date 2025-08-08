package org.example.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime orderDate;
    private String orderStatus;
    private List<OrderItemResponse> items;
}
