package org.example.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {

    private Long id;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotNull
    private CustomerType customerType;

    @NotNull
    private LocalDate orderDate;
}