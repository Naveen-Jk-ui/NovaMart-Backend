package com.nm.novamart.Service;

import com.nm.novamart.Dto.CartItemResponseDto;
import com.nm.novamart.Dto.CartRequestDto;
import com.nm.novamart.Entity.Cart;
import com.nm.novamart.Entity.CartItems;
import com.nm.novamart.Entity.Product;
import com.nm.novamart.Entity.User;
import com.nm.novamart.Mapper.CartItemMapper;
import com.nm.novamart.Repository.CartItemRepository;
import com.nm.novamart.Repository.CartRepository;
import com.nm.novamart.Repository.ProductRepository;
import com.nm.novamart.Repository.UserRepository;
import com.nm.novamart.Utility.PriceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public void addToCart(UUID userId, UUID productId, int quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        if(product.getQuantity() < quantity || quantity <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if(user.getCart()==null){
            Cart cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
            cartRepository.save(cart);
        }

        CartItems existingItem = user.getCart().getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if(!(existingItem == null)) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setSubtotal(product.getPrice()*quantity);

        }else {
            CartItems newItem = CartItemMapper.toCartItem(product, quantity, user);
            user.getCart().getItems().add(newItem);
        }

        double total = PriceCalculator.getTotalPrice(user.getCart());
        user.getCart().setTotalPrice(total);

        cartRepository.save(user.getCart());

    }

    public List<CartItemResponseDto> getCartItems(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Cart cart = user.getCart();

        return CartItemMapper.toCartResponse(cart);
    }

    @Transactional
    public List<CartItemResponseDto> updateCartItem(CartRequestDto cartRequestDto, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Cart cart = user.getCart();
        Product product = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        if(product.getQuantity() < cartRequestDto.getQuantity() ||  product.getQuantity() <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        CartItems cartItem = cart.getItems().stream()
                        .filter(cartItems -> cartItems.getProduct().getId().equals(cartRequestDto.getProductId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Product not found!"));

        cartItem.setQuantity(cartRequestDto.getQuantity());
        cartItem.setSubtotal(product.getPrice()*cartRequestDto.getQuantity());

        cartItemRepository.save(cartItem);

        double total = PriceCalculator.getTotalPrice(user.getCart());
        user.getCart().setTotalPrice(total);
        cartRepository.save(user.getCart());
        return CartItemMapper.toCartResponse(cart);

    }

}
