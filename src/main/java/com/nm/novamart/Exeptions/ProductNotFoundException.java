package com.nm.novamart.Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(UUID productId) {
        super("Product not found with id: " + productId);
    }

    public ProductNotFoundException(String productName) {
        super("Product not found with name: " + productName);
    }
}
