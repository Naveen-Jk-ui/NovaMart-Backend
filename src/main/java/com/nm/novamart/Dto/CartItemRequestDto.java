package com.nm.novamart.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {

    @NotNull
    private UUID productId;

    @NotNull
    private int quantity;
}
