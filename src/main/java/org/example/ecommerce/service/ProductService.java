package org.example.ecommerce.service;

import org.example.ecommerce.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Long id, Product updatedProduct);

    void  deleteProduct(Long id);

    List<Product> getProductByCategory(String category);

    Product updateStock(Long productId, Integer newStock);

}
