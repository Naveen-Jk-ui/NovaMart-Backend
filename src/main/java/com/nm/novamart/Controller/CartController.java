package com.nm.novamart.Controller;


import com.nm.novamart.Dto.CartItemDeleteRequestDto;
import com.nm.novamart.Dto.CartItemResponseDto;
import com.nm.novamart.Dto.CartItemRequestDto;
import com.nm.novamart.Entity.Product;
import com.nm.novamart.Repository.ProductRepository;
import com.nm.novamart.Service.CartServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("api/cart")
public class CartController {
    private final CartServiceImpl cartService;
    private final ProductRepository productRepository;

    @GetMapping("{userId}")
    public ResponseEntity<List<CartItemResponseDto>> getUserCart(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(cartService.getCartItems(userId));

    }

    @PostMapping("{userId}")
    public ResponseEntity<Product> addToCart(@RequestBody CartItemRequestDto cartItemRequestDto, @PathVariable UUID userId) {
        cartService.addToCart(userId, cartItemRequestDto.getProductId(), cartItemRequestDto.getQuantity());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productRepository.findById(cartItemRequestDto.getProductId()).get());
    }

    @PutMapping("{userId}")
    public ResponseEntity<List<CartItemResponseDto>> updateCartItems(@RequestBody CartItemRequestDto cartItemRequestDto, @PathVariable UUID userId) {
        List<CartItemResponseDto> cartItems = cartService.updateCartItem(cartItemRequestDto,  userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartItems);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<CartItemDeleteRequestDto> deleteCartItems(@PathVariable UUID userId, @RequestBody CartItemDeleteRequestDto deleteRequest) {
        cartService.deleteCartItems(userId, deleteRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

}
