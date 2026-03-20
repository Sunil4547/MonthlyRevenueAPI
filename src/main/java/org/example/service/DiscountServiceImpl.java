package org.example.service;

import org.example.model.CustomerType;
import org.example.model.OrderDTO;
import org.osgi.service.component.annotations.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Override
    public BigDecimal applyDiscount(OrderDTO order) {

        if (order.getCustomerType() == CustomerType.PREMIUM) {
            return order.getAmount().multiply(BigDecimal.valueOf(0.9)); // ✅ 10% discount
        }

        return order.getAmount();
    }
}