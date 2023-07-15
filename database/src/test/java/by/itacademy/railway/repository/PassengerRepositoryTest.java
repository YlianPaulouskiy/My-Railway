package by.itacademy.railway.repository;

import by.itacademy.railway.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class PassengerRepositoryTest {

    private final PassengerRepository passengerRepository;
    private final static Long USER_ID = 1L;
    private final static Integer EXPECTED_SIZE = 3;
    private final static Integer PAGE_EXPECT_SIZE = 2;

    @Test
    void whenFindAllByUserIdExpect3Passengers() {
        assertThat(passengerRepository.findAllByUserId(USER_ID)).hasSize(EXPECTED_SIZE);
    }

    @Test
    void whenFindAllByUserIdWithPageableExpectPageSizeAmountPassengers() {
        assertThat(passengerRepository.findAllByUserId(USER_ID, Pageable.ofSize(PAGE_EXPECT_SIZE)))
                .hasSize(PAGE_EXPECT_SIZE);
    }
}
