package org.example.ecommerce.request;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orderItem_request")
public class OrderItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Integer quantity;
}
