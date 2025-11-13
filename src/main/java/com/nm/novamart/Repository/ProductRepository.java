package com.nm.novamart.Repository;

import com.nm.novamart.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByCategory(String category);

    @Transactional(readOnly = true)
    Product getProductByName(String productName);
    Product getByNameIgnoreCase(String name);
    List<Product> getProductByCategoryIgnoreCase(String category);
}
