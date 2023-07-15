package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.repository.OrderRepository;
import by.itacademy.railway.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.DoubleStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class OrderServiceIT {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final static Integer DEFAULT_SIZE = 3;
    private final static Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private final static Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private final static Long ORDER_ID = 1L;
    private final static Integer PAGE_EXPECT_SIZE = 2;
    private final static Long USER_ID = 1L;

    @Test
    void whenFindAllByUserIdExpect3Orders() {
        assertThat(orderService.findAllByUserId(USER_ID)).hasSize(DEFAULT_SIZE);
    }

    @Test
    void whenFindAllByUserIdWithPageableExpectPageSizeAmountOrders() {
        assertThat(orderService.findAllByUserId(USER_ID, Pageable.ofSize(PAGE_EXPECT_SIZE)))
                .hasSize(PAGE_EXPECT_SIZE);
    }

    @Test
    void whenFindUserIdExpectOrderWithThatId() {
        var orderOpt = orderService.findById(ORDER_ID);
        assertTrue(orderOpt.isPresent());
        assertEquals(orderOpt.get().getId(), ORDER_ID);
    }

    @Test
    void whenCreateExpectIncreaseFullSizeBy1() {
        assertThat(orderRepository.findAllByUserId(USER_ID)).hasSize(DEFAULT_SIZE);
        assertTrue(orderService.create(USER_ID).isPresent());
        assertThat(orderRepository.findAllByUserId(USER_ID)).hasSize(CREATED_SIZE);
    }

    @Test
    void whenRemoveExpectReduceFullSizeBy1() {
        assertThat(orderRepository.findAllByUserId(USER_ID)).hasSize(DEFAULT_SIZE);
        orderService.remove(ORDER_ID);
        assertFalse(orderRepository.existsById(ORDER_ID));
        assertThat(orderRepository.findAllByUserId(USER_ID)).hasSize(REMOVED_SIZE);
    }

}
