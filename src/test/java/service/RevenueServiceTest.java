package service;

import org.example.model.CustomerType;
import org.example.model.OrderDTO;
import org.example.repository.OrderRepository;
import org.example.service.DiscountService;
import org.example.service.RevenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class RevenueServiceTest {

    @Mock
    private DiscountService discountService;

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private RevenueService revenueService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCalculateMonthlyRevenue_withDiscount_andIgnoreInvalidOrders() {

        OrderDTO validPremium = new OrderDTO(1L, new BigDecimal("1000"),
                CustomerType.PREMIUM, LocalDate.of(2026, 3, 10));

        OrderDTO validRegular = new OrderDTO(2L, new BigDecimal("500"),
                CustomerType.REGULAR, LocalDate.of(2026, 3, 15));

        OrderDTO negativeAmount = new OrderDTO(3L, new BigDecimal("-100"),
                CustomerType.REGULAR, LocalDate.of(2026, 3, 20));

        OrderDTO nullAmount = new OrderDTO(4L, null,
                CustomerType.REGULAR, LocalDate.of(2026, 3, 25));

        when(repository.findAll()).thenReturn(
                Arrays.asList(validPremium, validRegular, negativeAmount, nullAmount)
        );

        when(discountService.applyDiscount(validPremium))
                .thenReturn(new BigDecimal("900")); // 10% discount

        when(discountService.applyDiscount(validRegular))
                .thenReturn(new BigDecimal("500"));

        Map<YearMonth, BigDecimal> result = revenueService.calculateMonthlyRevenue();

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("1400"), result.get(YearMonth.of(2026, 3)));

        verify(discountService, times(2)).applyDiscount(any());
    }
}