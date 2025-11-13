package com.nm.novamart.Service;

import com.nm.novamart.Dto.ProductRequestDto;
import com.nm.novamart.Dto.ProductUpdateReqDto;
import com.nm.novamart.Entity.Product;
import com.nm.novamart.Exeptions.DuplicateProductException;
import com.nm.novamart.Exeptions.ProductNotFoundException;
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
    private final CartServiceImpl cartService;

    public Product addProduct(ProductRequestDto productReqDto) {
        if(productRepository.existsByName(productReqDto.getName())) {
            throw new DuplicateProductException(productReqDto.getName());
        }
        Product newProduct =  ProductMapper.toProduct(productReqDto);
        return productRepository.save(newProduct);
    }

    public Product updateProduct(ProductUpdateReqDto productReqDto) {

        Product product =  productRepository.findById(productReqDto.getId())
                .orElseThrow(() -> new ProductNotFoundException(productReqDto.getId()));

        if(productRepository.existsByNameAndIdNot(productReqDto.getName(), productReqDto.getId())) {
            throw new DuplicateProductException(productReqDto.getName());
        }

        Product updateProduct = ProductMapper.updateProduct(product, productReqDto);

        cartService.updateAllCartItems(updateProduct);

        return productRepository.save(updateProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(UUID productId) {
        if(productRepository.findById(productId).isEmpty()) {
            throw new ProductNotFoundException(productId);
        }
        productRepository.deleteById(productId);
    }

}
