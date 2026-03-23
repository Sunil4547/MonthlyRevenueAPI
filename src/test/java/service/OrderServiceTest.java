package service;

import org.example.model.CustomerType;
import org.example.model.OrderDTO;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService service;

    public OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOrder_whenIdExists() {

        OrderDTO order = new OrderDTO(1L,
                new BigDecimal("1000"),
                CustomerType.REGULAR,
                LocalDate.now());

        when(repository.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO result = service.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowException_whenOrderNotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getById(1L));
    }
}