package com.nm.novamart.Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateProductException extends BaseExeption {
    public DuplicateProductException(String name) {
        super("Product Already Exists with name: " + name);
    }
}
