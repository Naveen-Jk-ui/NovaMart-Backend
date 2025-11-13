package com.nm.novamart.Repository;

import com.nm.novamart.Entity.Cart;
import com.nm.novamart.Entity.CartItems;
import com.nm.novamart.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItems,Long> {
    List<CartItems> findByProduct(Product product);
}
