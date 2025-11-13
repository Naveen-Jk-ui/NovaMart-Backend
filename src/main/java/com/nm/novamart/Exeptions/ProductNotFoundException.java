package com.nm.novamart.Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends BaseExeption {
    public ProductNotFoundException(UUID productId) {
        super("Product not found with id: " + productId);
    }
}
