package com.nm.novamart.Service;

import com.nm.novamart.Dto.*;
import com.nm.novamart.Entity.*;
import com.nm.novamart.Exeptions.InsufficientStockException;
import com.nm.novamart.Exeptions.ProductNotFoundException;
import com.nm.novamart.Exeptions.UserNotFoundException;
import com.nm.novamart.Mapper.CartItemMapper;
import com.nm.novamart.Repository.*;
import com.nm.novamart.Utility.PriceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public void addToCart(UUID userId, UUID productId, int quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if(product.getQuantity() < quantity || quantity <= 0) {
            throw new InsufficientStockException(product.getName(),product.getQuantity(),quantity);
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

        }else {
            CartItems newItem = CartItemMapper.toCartItem(product, quantity, user);
            user.getCart().getItems().add(newItem);
        }

        double total = PriceCalculator.getTotalPrice(user.getCart());
        user.getCart().setTotalPrice(total);

        cartRepository.save(user.getCart());

    }

    @Transactional(readOnly = true)
    public List<CartItemResponseDto> getCartItems(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Cart cart = user.getCart();

        return CartItemMapper.toCartResponse(cart);
    }

    @Transactional
    public List<CartItemResponseDto> updateCartItem(CartItemRequestDto cartItemRequestDto, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Cart cart = user.getCart();
        Product product = productRepository.findById(cartItemRequestDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(cartItemRequestDto.getProductId()));

        if(product.getQuantity() < cartItemRequestDto.getQuantity() ||  product.getQuantity() <= 0) {
            throw new InsufficientStockException(product.getName(),product.getQuantity(), cartItemRequestDto.getQuantity());
        }

        CartItems cartItem = cart.getItems().stream()
                        .filter(cartItems -> cartItems.getProduct().getId().equals(cartItemRequestDto.getProductId()))
                        .findFirst()
                        .orElseThrow(() -> new ProductNotFoundException(cartItemRequestDto.getProductId()));

        cartItem.setQuantity(cartItemRequestDto.getQuantity());

        double total = PriceCalculator.getTotalPrice(user.getCart());
        user.getCart().setTotalPrice(total);

        cartItemRepository.save(cartItem);
        cartRepository.save(user.getCart());

        return CartItemMapper.toCartResponse(cart);

    }

    @Transactional
    public void updateAllCartItems(Product product){
        List<CartItems> allCartItems = cartItemRepository.findByProduct(product);
        for(CartItems cartItem : allCartItems) {
            cartItem.prePersist();
            cartItemRepository.save(cartItem);
        }
    }

    @Transactional
    public void deleteCartItems(UUID userId, CartItemDeleteRequestDto deleteRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Cart cart = user.getCart();

        CartItems cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(deleteRequest.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(deleteRequest.getProductId()));

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cartRepository.save(user.getCart());
        cart.setTotalPrice(PriceCalculator.getTotalPrice(user.getCart()));

    }

}
