package org.example.ecommerce.dto;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Long id;
    private ProductResponse product;
    private Integer quantity;

}
