package ru.krutikov.products_and_shops_list.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuickProductRequest {
    @NotBlank
    private String name;
    private String category;
}