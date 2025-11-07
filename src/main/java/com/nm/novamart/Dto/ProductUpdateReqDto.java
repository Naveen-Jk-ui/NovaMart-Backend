package com.nm.novamart.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateReqDto {

    private UUID Id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
//    private String productImageUrl;

}
