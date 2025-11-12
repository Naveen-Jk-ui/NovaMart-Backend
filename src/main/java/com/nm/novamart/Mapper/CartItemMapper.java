package com.nm.novamart.Mapper;

import com.nm.novamart.Dto.CartItemResponseDto;
import com.nm.novamart.Entity.Cart;
import com.nm.novamart.Entity.CartItems;
import com.nm.novamart.Entity.Product;
import com.nm.novamart.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class CartItemMapper {

    public static List<CartItemResponseDto> toCartResponse(Cart cart) {
        if (cart == null) {
            return null;
        }
        List<CartItems> cartItems = cart.getItems();
        List<CartItemResponseDto> cartResponse = new ArrayList<>();

        for(CartItems cartItem : cartItems) {

            CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();

            cartItemResponseDto.setProductId(cartItem.getProduct().getId());
            cartItemResponseDto.setProductName(cartItem.getProduct().getName());
            cartItemResponseDto.setQuantity(cartItem.getQuantity());
            cartItemResponseDto.setSubTotal(cartItem.getSubtotal());
            Product product = cartItem.getProduct();
            cartItemResponseDto.setAvailability(product.getQuantity() >= cartItem.getQuantity());
            cartResponse.add(cartItemResponseDto);
        }
        return cartResponse;
    }




    public static CartItems toCartItem (Product product, int quantity, User user) {
        CartItems cartItem = new CartItems();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setSubtotal(product.getPrice()*quantity);
        cartItem.setCart(user.getCart());
        return cartItem;
    }
}
