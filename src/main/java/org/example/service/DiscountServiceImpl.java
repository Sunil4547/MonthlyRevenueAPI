package org.example.service;

import org.osgi.service.component.annotations.Component;

@Component(service = DiscountService.class)
public class DiscountServiceImpl implements DiscountService {

    @Override
    public double applyDiscount(double amount) {
        return amount * 0.95; // 5% discount
    }
}