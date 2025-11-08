package com.nm.novamart.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Name required")
    private String name;


    private String description;

    @NotNull(message = "Price Required")
    private double price;

    @NotNull(message = "Quantity cant be null")
    private int quantity;

    @NotNull(message = "Category Cant be blank")
    private String category;

//    private String ImgURL;

}
