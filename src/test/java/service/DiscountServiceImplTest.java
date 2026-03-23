package service;

import org.example.model.CustomerType;
import org.example.model.OrderDTO;
import org.example.service.DiscountService;
import org.example.service.DiscountServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceImplTest {

    private final DiscountService discountService = new DiscountServiceImpl();

    @Test
    void shouldApply10PercentDiscount_forPremiumCustomer() {

        OrderDTO order = new OrderDTO(1L,
                new BigDecimal("1000"),
                CustomerType.PREMIUM,
                LocalDate.now());

        BigDecimal result = discountService.applyDiscount(order);

        assertEquals(new BigDecimal("900.0"), result);
    }

    @Test
    void shouldNotApplyDiscount_forRegularCustomer() {

        OrderDTO order = new OrderDTO(1L,
                new BigDecimal("1000"),
                CustomerType.REGULAR,
                LocalDate.now());

        BigDecimal result = discountService.applyDiscount(order);

        assertEquals(new BigDecimal("1000"), result);
    }
}