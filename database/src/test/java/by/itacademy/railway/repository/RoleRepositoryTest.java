package by.itacademy.railway.repository;

import by.itacademy.railway.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class RoleRepositoryTest {

    private final RoleRepository roleRepository;
    private final static String ROLE = "USER";

    @Test
    void whenFindByRoleExpectRoleWithThatName() {
        var roleOpt = roleRepository.findByRole(ROLE);
        assertTrue(roleOpt.isPresent());
        assertEquals(roleOpt.get().getRole(), ROLE);
    }
}
