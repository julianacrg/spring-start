package br.com.spring.api.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRecordDto(
    @NotBlank(message = "Name is required and cannot be blank.")
    @Size(min = 10, max = 50, message = "The name must have between 10 and 50 characters.")
    String name,

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.")
    @Digits(integer = 10, fraction = 2, message = "The price must have a maximum of 10 whole digits and 2 decimal places.")
    BigDecimal price,

    @Size(max = 500, message = "The description can have a maximum of 500 characters.")
    String description,

    @PositiveOrZero(message = "Stock cannot be negative.")
    int stock
) {}
