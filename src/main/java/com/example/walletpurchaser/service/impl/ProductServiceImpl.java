package com.example.walletpurchaser.service.impl;

import com.example.walletpurchaser.exception.ResourceNotFoundException;
import com.example.model.Product;
import com.example.walletpurchaser.Repository.ProductRepository;
import com.example.walletpurchaser.service.ProductService;
import com.example.walletpurchaser.service.validation.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    @Override
    public Product createProduct(Product product) {
        productValidator.validate(product);
        product.setActive(true);
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

//    @Override
//    public List<Product> searchProductsByName(String name) {
//        return productRepository.findByNameContainingIgnoreCase(name);
//    }

//    @Override
//    public List<Product> findProductsInPriceRange(double minPrice, double maxPrice) {
//        return productRepository.findByPriceBetween(minPrice, maxPrice);
//    }

    @Override
    public Page<Product> getProductsPaged(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productValidator.validate(updatedProduct);

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setActive(updatedProduct.isActive());

        return productRepository.save(existing);
    }

    @Override
    public void deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
    }
}
