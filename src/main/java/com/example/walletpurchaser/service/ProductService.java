package com.example.walletpurchaser.service;



import com.example.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);
    Optional<Product> getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getActiveProducts();
   // List<Product> searchProductsByName(String name);
  //  List<Product> findProductsInPriceRange(double minPrice, double maxPrice);
    Page<Product> getProductsPaged(Pageable pageable);
    Product updateProduct(Long id, Product updatedProduct);
    void deactivateProduct(Long id);
}

