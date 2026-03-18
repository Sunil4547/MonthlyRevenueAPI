package org.example.service;

import org.example.model.OrderDTO;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public OrderDTO create(OrderDTO order) {
        return repository.save(order);
    }

    public OrderDTO getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderDTO> getByMonth(String month) {
        YearMonth ym = YearMonth.parse(month);

        return repository.findAll().stream()
                .filter(o -> YearMonth.from(o.getOrderDate()).equals(ym))
                .collect(Collectors.toList());
    }
}