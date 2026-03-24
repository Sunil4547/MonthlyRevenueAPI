package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service responsible for calculating monthly revenue.
 */
@Service
@RequiredArgsConstructor
public class RevenueService {

    private static final Logger log = LoggerFactory.getLogger(RevenueService.class);

    private final DiscountService discountService;
    private final OrderRepository repository;

    /**
     * Calculates monthly revenue grouped by YearMonth.
     *
     * Business Rules:
     * - Ignore null orders
     * - Ignore orders with null or negative amounts
     * - Apply discount for PREMIUM customers
     *
     * @return map of YearMonth to total revenue
     */
    public Map<YearMonth, BigDecimal> calculateMonthlyRevenue() {

        log.info("Starting monthly revenue calculation");

        Map<YearMonth, BigDecimal> revenueMap = repository.findAll().stream()

                // Ignore null order objects (defensive programming)
                .filter(Objects::nonNull)

                // Ignore orders with null or negative amounts
                .filter(o -> o.getAmount() != null &&
                        o.getAmount().compareTo(BigDecimal.ZERO) > 0)

                // Group by YearMonth and calculate total revenue
                .collect(Collectors.groupingBy(
                        o -> YearMonth.from(o.getOrderDate()),

                        // Apply discount before summing up revenue
                        Collectors.mapping(
                                discountService::applyDiscount,

                                // Sum all discounted values
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        log.info("Revenue calculation completed. Months processed: {}", revenueMap.size());

        return revenueMap;
    }
}