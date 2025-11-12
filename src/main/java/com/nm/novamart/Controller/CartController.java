package com.nm.novamart.Controller;


import com.nm.novamart.Dto.CartItemResponseDto;
import com.nm.novamart.Dto.CartRequestDto;
import com.nm.novamart.Entity.Cart;
import com.nm.novamart.Entity.CartItems;
import com.nm.novamart.Entity.Product;
import com.nm.novamart.Repository.ProductRepository;
import com.nm.novamart.Repository.UserRepository;
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
    private final UserRepository userRepository;

    @GetMapping("{userId}")
    public ResponseEntity<List<CartItemResponseDto>> getUserCart(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(cartService.getCartItems(userId));

    }

    @PostMapping("{userId}")
    public ResponseEntity<Product> addToCart(@RequestBody CartRequestDto cartRequestDto,  @PathVariable UUID userId) {
        cartService.addToCart(userId, cartRequestDto.getProductId(), cartRequestDto.getQuantity());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productRepository.findById(cartRequestDto.getProductId()).get());
    }

    @PutMapping("{userId}")
    public ResponseEntity<List<CartItemResponseDto>> updateCartItems(@RequestBody CartRequestDto cartRequestDto,  @PathVariable UUID userId) {
        List<CartItemResponseDto> cartItems = cartService.updateCartItem(cartRequestDto,  userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartItems);
    }

}
