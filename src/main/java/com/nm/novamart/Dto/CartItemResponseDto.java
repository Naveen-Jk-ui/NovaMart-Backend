package com.nm.novamart.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemResponseDto {
    private UUID productId;
    private String productName;
    private int quantity;
    private double subTotal;
    private boolean availability;

}
