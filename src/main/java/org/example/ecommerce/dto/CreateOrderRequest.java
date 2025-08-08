package org.example.ecommerce.dto;

import lombok.Data;
import org.example.ecommerce.request.OrderItemRequest;

import java.util.List;

@Data
public class CreateOrderRequest {

    private String name;
    private String email;
    private Long id;
    private List<OrderItemRequest> items;

}
