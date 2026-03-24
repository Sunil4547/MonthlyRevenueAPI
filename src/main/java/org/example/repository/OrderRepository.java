package org.example.repository;

import org.example.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory repository for storing orders.
 *
 * Uses ConcurrentHashMap for thread-safe operations.
 */
@Repository
public class OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderRepository.class);

    // Thread-safe in-memory storage
    private final Map<Long, OrderDTO> db = new ConcurrentHashMap<>();

    // Thread-safe ID generator
    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * Saves a new order.
     *
     * @param order order to save
     * @return saved order with generated ID
     */
    public OrderDTO save(OrderDTO order) {
        long id = idCounter.getAndIncrement(); // thread-safe increment
        order.setId(id);

        db.put(id, order);

        log.debug("Order saved with id: {}", id);

        return order;
    }

    /**
     * Finds an order by ID.
     *
     * @param id order identifier
     * @return optional order
     */
    public Optional<OrderDTO> findById(Long id) {
        log.debug("Fetching order with id: {}", id);
        return Optional.ofNullable(db.get(id));
    }

    /**
     * Retrieves all orders.
     *
     * @return list of all orders
     */
    public List<OrderDTO> findAll() {
        log.debug("Fetching all orders. Total count: {}", db.size());
        return new ArrayList<>(db.values());
    }

    /**
     * Clears all stored orders (useful for testing).
     */
    public void clear() {
        db.clear();
        idCounter.set(1);
        log.debug("Repository cleared");
    }
}