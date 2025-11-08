package com.nm.novamart.Service;

import com.nm.novamart.Dto.ProductRequestDto;
import com.nm.novamart.Dto.ProductUpdateReqDto;
import com.nm.novamart.Entity.Product;
import com.nm.novamart.Mapper.ProductMapper;
import com.nm.novamart.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    public Product addProduct(ProductRequestDto productReqDto) {
        if(productRepository.existByName(productReqDto.getName())) {
            throw new RuntimeException("Product Already Exist");
        }
        Product newProduct =  ProductMapper.toProduct(productReqDto);
        return productRepository.save(newProduct);
    }

    public Product updateProduct(ProductUpdateReqDto productReqDto) {

        Product product =  productRepository.findById(productReqDto.getId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        if(productReqDto.getName().equals(product.getName()) || productRepository.existByName(productReqDto.getName())) {
            throw new RuntimeException("Product Name Already Exist");
        }

        Product updateProduct = ProductMapper.updateProduct(product, productReqDto);
        return productRepository.save(updateProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(UUID productId) {
        if(productRepository.findById(productId).isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(productId);
    }

}
