package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final DiscountService discountService;
    private final OrderRepository repository;

    public Map<YearMonth, BigDecimal> calculateMonthlyRevenue() {

        return repository.findAll().stream()
                .filter(Objects::nonNull)

                // Explicitly ignore null & negative
                .filter(o -> o.getAmount() != null &&
                        o.getAmount().compareTo(BigDecimal.ZERO) > 0)

                .collect(Collectors.groupingBy(
                        o -> YearMonth.from(o.getOrderDate()),
                        Collectors.mapping(
                                discountService::applyDiscount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
    }
}