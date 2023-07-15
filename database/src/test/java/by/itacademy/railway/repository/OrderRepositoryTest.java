package by.itacademy.railway.repository;

import by.itacademy.railway.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class OrderRepositoryTest {

    private final OrderRepository orderRepository;
    private final static Long USER_ID = 1L;
    private final static Integer EXPECT_SIZE = 3;
    private final static Integer PAGE_EXPECT_SIZE = 2;

    @Test
    void whenFindAllByUserIdExpect3Orders() {
        assertThat(orderRepository.findAllByUserId(USER_ID)).hasSize(EXPECT_SIZE);
    }

    @Test
    void whenFindAllByUserIdWithPageableExpectPageSizeAmountOrders() {
        assertThat(orderRepository.findAllByUserId(USER_ID, Pageable.ofSize(PAGE_EXPECT_SIZE)))
                .hasSize(PAGE_EXPECT_SIZE);
    }

}
