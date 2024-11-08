package br.com.spring.api.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRecordDto(
    @NotBlank @Size(min = 10, max = 50) String name,
    @NotNull @DecimalMin("0.0") @Digits(integer = 10, fraction = 2) BigDecimal price,
    @Size(max = 500) String description,
    @PositiveOrZero int stock) {
}
