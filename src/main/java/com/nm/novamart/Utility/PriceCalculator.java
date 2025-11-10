package com.nm.novamart.Utility;

import com.nm.novamart.Entity.Cart;
import com.nm.novamart.Entity.CartItems;
import com.nm.novamart.Entity.Product;

import java.util.List;

public class PriceCalculator {

    public static double getTotalPrice(Cart cart){
        double price = 0;
        List<CartItems> cartItems = cart.getItems();

        for(CartItems item : cartItems){
            price += item.getProduct().getPrice()*item.getQuantity();
        }
        return price;
    }

}
