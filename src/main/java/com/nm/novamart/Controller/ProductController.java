package com.nm.novamart.Controller;

import com.nm.novamart.Dto.ProductRequestDto;
import com.nm.novamart.Dto.ProductUpdateReqDto;
import com.nm.novamart.Entity.Product;
import com.nm.novamart.Service.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/products")
public class ProductController {

    private final ProductServiceImpl productService;

    @Transactional
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto productReqDto) {
        Product product = productService.addProduct(productReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody ProductUpdateReqDto productReqDto) {
        Product product = productService.updateProduct(productReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProducts());
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }




}
