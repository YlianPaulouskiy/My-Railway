package by.itacademy.railway.repository;

import by.itacademy.railway.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class UserRepositoryTest {

    private final UserRepository userRepository;
    private final static Integer PAGE_EXPECT_SIZE = 1;
    private final static String EMAIL = "maxik@gmail.com";

    @Test
    void whenFindAllByPageableExpectPageSizeAmountUsers() {
        assertThat(userRepository.findAll(Pageable.ofSize(PAGE_EXPECT_SIZE)))
                .hasSize(PAGE_EXPECT_SIZE);
    }

    @Test
    void whenFindUserByEmailExpectUserWithThatEmail() {
        var userOpt = userRepository.findByEmail(EMAIL);
        assertTrue(userOpt.isPresent());
        assertEquals(userOpt.get().getEmail(), EMAIL);
    }
}
