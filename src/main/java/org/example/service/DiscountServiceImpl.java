package org.example.service;

import org.example.model.CustomerType;
import org.example.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Default implementation of DiscountService.
 *
 * Applies 10% discount for PREMIUM customers.
 */
@Service
public class DiscountServiceImpl implements DiscountService {

    private static final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    // 10% discount
    private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.10);

    @Override
    public BigDecimal applyDiscount(OrderDTO order) {

        // Defensive check (avoid NullPointerException)
        if (order == null || order.getAmount() == null) {
            log.warn("Invalid order received for discount calculation: {}", order);
            return BigDecimal.ZERO;
        }

        BigDecimal amount = order.getAmount();

        // Apply discount for PREMIUM customers
        if (order.getCustomerType() == CustomerType.PREMIUM) {
            log.debug("Applying 10% discount for PREMIUM customer");

            return amount.subtract(amount.multiply(DISCOUNT_RATE));
        }

        // No discount for other customers
        return amount;
    }
}