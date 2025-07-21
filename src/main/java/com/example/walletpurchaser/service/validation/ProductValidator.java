package com.example.walletpurchaser.service.validation;

import com.example.walletpurchaser.exception.ProductValidationException;
import com.example.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Component
public class ProductValidator {

    public void validate(Product product) {
        if (!StringUtils.hasText(product.getName())) {
            throw new ProductValidationException("Product name cannot be blank.");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductValidationException("Product price must be greater than 0.");
        }
    }
}