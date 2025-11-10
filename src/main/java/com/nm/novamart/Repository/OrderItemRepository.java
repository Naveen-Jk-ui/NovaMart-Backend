package com.nm.novamart.Repository;

import com.nm.novamart.Entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItems, Integer> {
}
