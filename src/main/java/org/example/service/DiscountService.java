package org.example.service;

import org.example.model.OrderDTO;

import java.math.BigDecimal;

/**
 * Service interface for applying discounts on orders.
 *
 * Implementations can define different discount strategies
 * based on customer type or business rules.
 */
public interface DiscountService {

    /**
     * Applies discount to the given order based on business rules.
     *
     * Example:
     * - PREMIUM customers → 10% discount
     * - REGULAR customers → no discount
     *
     * @param order the order for which discount is applied
     * @return discounted amount
     */
    BigDecimal applyDiscount(OrderDTO order);
}