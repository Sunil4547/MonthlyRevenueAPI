package org.example.service;

import org.example.model.OrderDTO;
import org.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for handling order-related operations.
 */
@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates and saves a new order.
     *
     * @param order order details
     * @return saved order with generated ID
     */
    public OrderDTO create(OrderDTO order) {
        log.info("Saving new order: {}", order);

        OrderDTO savedOrder = repository.save(order);

        log.info("Order saved successfully with id: {}", savedOrder.getId());

        return savedOrder;
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id order identifier
     * @return order details
     * @throws RuntimeException if order not found
     */
    public OrderDTO getById(Long id) {
        log.info("Fetching order with id: {}", id);

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", id);
                    return new RuntimeException("Order not found");
                });
    }

    /**
     * Retrieves all orders for a given month.
     *
     * @param month month in format YYYY-MM
     * @return list of orders for the specified month
     */
    public List<OrderDTO> getByMonth(String month) {
        log.info("Fetching orders for month: {}", month);

        // Parse input string to YearMonth (throws exception if invalid format)
        YearMonth ym = YearMonth.parse(month);

        List<OrderDTO> filteredOrders = repository.findAll().stream()
                // Filter orders matching the given YearMonth
                .filter(o -> YearMonth.from(o.getOrderDate()).equals(ym))
                .collect(Collectors.toList());

        log.info("Found {} orders for month {}", filteredOrders.size(), month);

        return filteredOrders;
    }
}