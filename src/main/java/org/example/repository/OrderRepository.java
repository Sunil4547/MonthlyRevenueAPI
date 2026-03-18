package org.example.repository;

import org.example.model.OrderDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {

    private final Map<Long, OrderDTO> db = new ConcurrentHashMap<>();
    private long idCounter = 1;

    public OrderDTO save(OrderDTO order) {
        order.setId(idCounter++);
        db.put(order.getId(), order);
        return order;
    }

    public Optional<OrderDTO> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    public List<OrderDTO> findAll() {
        return new ArrayList<>(db.values());
    }
}