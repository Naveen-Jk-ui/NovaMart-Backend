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

    @Transactional
    public Product addProduct(ProductRequestDto productReqDto) {
        if(productRepository.existsByName(productReqDto.getName())) {
            throw new DuplicateProductException(productReqDto.getName());
        }
        Product newProduct =  ProductMapper.toProduct(productReqDto);
        return productRepository.save(newProduct);
    }

    @Transactional
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

    @Transactional
    public void deleteProduct(UUID productId) {
        if(productRepository.findById(productId).isEmpty()) {
            throw new ProductNotFoundException(productId);
        }
        productRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductByName(String productName) {

        if (!(productRepository.existsByNameIgnoreCase(productName))) {
            throw new ProductNotFoundException(productName);
        }
        return productRepository.getByNameIgnoreCase(productName);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.getProductByCategoryIgnoreCase(category);
    }
}
