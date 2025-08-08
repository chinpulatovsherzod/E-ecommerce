package org.example.ecommerce.service.serviceImpl;


import org.example.ecommerce.model.Product;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product createProduct(Product product) {
        Product product1 = new Product();
        product1.setIsActive(true);
        product1.setCreatedAt(LocalDateTime.now());
        product1.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product1);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Maxsulot id orqali topilmadi" + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product exisitingProduct = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Maxsulot id orqali topilmadi" + id));

        exisitingProduct.setName(updatedProduct.getName());
        exisitingProduct.setPrice(updatedProduct.getPrice());
        exisitingProduct.setStock(updatedProduct.getStock());
        exisitingProduct.setCategory(updatedProduct.getCategory());
        exisitingProduct.setIsActive(updatedProduct.getIsActive());
        exisitingProduct.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(exisitingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);

    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException(" Maxsulot id orqali topilmadi" + productId));

        product.setStock(newStock);
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
}
