package com.nm.novamart.Mapper;

import com.nm.novamart.Dto.ProductRequestDto;
import com.nm.novamart.Dto.ProductUpdateReqDto;
import com.nm.novamart.Entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static Product toProduct(ProductRequestDto productReqDto) {
        if(productReqDto == null) {
            return null;
        }
        return Product.builder()
                .name(productReqDto.getName())
                .description(productReqDto.getDescription())
                .price(productReqDto.getPrice())
                .quantity(productReqDto.getQuantity())
                .category(productReqDto.getCategory())
                .build();
    }

    public static Product updateProduct(Product product,ProductUpdateReqDto productUpdateReqDto) {
        if(productUpdateReqDto == null ||  product == null) {
            return null;
        }
        product.setName(productUpdateReqDto.getName());
        product.setDescription(productUpdateReqDto.getDescription());
        product.setPrice(productUpdateReqDto.getPrice());
        product.setQuantity(productUpdateReqDto.getQuantity());
        product.setCategory(productUpdateReqDto.getCategory());
        return product;
    }

}
