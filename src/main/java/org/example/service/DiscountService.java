package org.example.service;

import org.example.model.OrderDTO;

import java.math.BigDecimal;

public interface DiscountService {
    BigDecimal applyDiscount(OrderDTO order);
}
