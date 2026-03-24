package org.example.controller;

import jakarta.validation.Valid;
import org.example.model.OrderDTO;
import org.example.service.OrderService;
import org.example.service.RevenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing orders and revenue operations.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService service;
    private final RevenueService revenueService;

    public OrderController(OrderService service, RevenueService revenueService) {
        this.service = service;
        this.revenueService = revenueService;
    }

    /**
     * Creates a new order.
     *
     * @param order the order request payload
     * @return created order with generated ID
     */
    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderDTO order) {
        log.info("Received request to create order: {}", order);

        OrderDTO createdOrder = service.create(order);

        log.info("Order created successfully with id: {}", createdOrder.getId());

        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Fetches an order by its ID.
     *
     * @param id the order ID
     * @return order details
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        log.info("Fetching order with id: {}", id);

        OrderDTO order = service.getById(id);

        return ResponseEntity.ok(order);
    }

    /**
     * Retrieves orders filtered by a specific month.
     *
     * @param month the month in format YYYY-MM
     * @return list of orders for the given month
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getByMonth(@RequestParam String month) {
        log.info("Fetching orders for month: {}", month);

        List<OrderDTO> orders = service.getByMonth(month);

        return ResponseEntity.ok(orders);
    }

    /**
     * Calculates monthly revenue grouped by YearMonth.
     *
     * Business Rules:
     * - Applies 10% discount for PREMIUM customers
     * - Ignores null or negative amounts
     *
     * @return map of YearMonth to total revenue
     */
    @GetMapping("/revenue")
    public ResponseEntity<Map<YearMonth, BigDecimal>> getRevenue() {
        log.info("Calculating monthly revenue");

        Map<YearMonth, BigDecimal> revenue = revenueService.calculateMonthlyRevenue();

        log.info("Revenue calculated successfully");

        return ResponseEntity.ok(revenue);
    }
}