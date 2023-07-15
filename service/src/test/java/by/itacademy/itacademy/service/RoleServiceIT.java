package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.repository.RoleRepository;
import by.itacademy.railway.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class RoleServiceIT {

    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final static Integer ROLE_ID = 1;
    private final static Integer DEFAULT_SIZE = 3;
    private final static Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private final static Integer REMOVE_ROLE_ID = 1;

    @Test
    void whenFindAllExpect3Roles() {
        assertThat(roleService.findAll()).hasSize(DEFAULT_SIZE);
    }

    @Test
    void whenFindByIdExpectRoleWithThatId() {
        var roleOpt = roleRepository.findById(ROLE_ID);
        assertTrue(roleOpt.isPresent());
        assertEquals(roleOpt.get().getId(), ROLE_ID);
    }

    @Test
    void whenRemoveExpectReduceFullSizeBy1() {
        assertThat(roleRepository.findAll()).hasSize(DEFAULT_SIZE);
        roleService.remove(REMOVE_ROLE_ID);
        assertThat(roleRepository.existsById(REMOVE_ROLE_ID)).isFalse();
        assertThat(roleRepository.findAll()).hasSize(REMOVED_SIZE);
    }

}
