package com.nm.novamart.Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientStockException extends BaseException {
    public InsufficientStockException(String productName, int available, int requested) {

        super(String.format("Insufficient stock for '%s'. Available: %d, Requested: %d",productName, available, requested));
    }
}
