package com.nm.novamart.Service;

import com.nm.novamart.Dto.ProductRequestDto;
import com.nm.novamart.Dto.ProductUpdateReqDto;
import com.nm.novamart.Entity.Product;
import com.nm.novamart.Mapper.ProductMapper;
import com.nm.novamart.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product addProduct(ProductRequestDto productReqDto) {
        if(!(productReqDto.getName().isEmpty())){
            throw new RuntimeException("Product Already Exist");
        }
        Product newProduct =  productMapper.toProduct(productReqDto);
        return productRepository.save(newProduct);
    }

    public Product updateProduct(ProductUpdateReqDto productReqDto) {
        if((productRepository.findById(productReqDto.getId()).isEmpty())) {
            throw new RuntimeException("Product not found");
        }
        Product product =  productRepository.findById(productReqDto.getId()).get();

        Product updateProduct = productMapper.updateProduct(product, productReqDto);
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
