package org.example.controller;

import jakarta.validation.Valid;
import org.example.model.OrderDTO;
import org.example.service.OrderService;
import org.example.service.RevenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;
    private final RevenueService revenueService;

    public OrderController(OrderService service, RevenueService revenueService) {
        this.service = service;
        this.revenueService = revenueService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderDTO order) {
        return new ResponseEntity<>(service.create(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getByMonth(@RequestParam String month) {
        return ResponseEntity.ok(service.getByMonth(month));
    }

    @GetMapping("/revenue")
    public ResponseEntity<Map<YearMonth, BigDecimal>> getRevenue() {
        return ResponseEntity.ok(revenueService.calculateMonthlyRevenue());
    }
}