package org.example.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private Boolean isActive;

}
