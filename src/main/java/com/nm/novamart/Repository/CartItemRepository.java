package com.nm.novamart.Repository;

import com.nm.novamart.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<Cart,Integer> {
}
