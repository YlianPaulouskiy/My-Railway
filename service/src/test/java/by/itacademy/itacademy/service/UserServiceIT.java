package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.dto.user.UserChangePasswordDto;
import by.itacademy.railway.dto.user.UserCreateEditDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserUpdateDto;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.repository.UserRepository;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class UserServiceIT {

    private final UserRepository userRepository;
    private final UserService userService;
    private final static Long FIND_ID = 1L;
    private final static Integer DEFAULT_SIZE = 3;
    private final static Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private final static Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private final static Integer PAGE_EXPECT_SIZE = 2;
    private final static String EMAIL = "maxik@gmail.com";
    private User rightUser;
    private UserCreateEditDto createUserDto;
    private UserUpdateDto userUpdateDto;
    private RoleReadDto updatedRole;
    private UserChangePasswordDto changePasswordDto;

    @BeforeEach
    void setUp() {
        userRepository.findById(FIND_ID).ifPresent(user -> rightUser = user);
        createUserDto = UserCreateEditDto.builder()
                .name("Andrey")
                .lastName("Nekrashevich")
                .email("axolm@gmail.com")
                .password("1qaz2wsx3edc")
                .build();
        updatedRole = RoleReadDto.builder().id(3).name("USER").build();
        userUpdateDto = UserUpdateDto.builder()
                .name("Andrey")
                .lastName("Nekrashevich")
                .email("axolm@gmail.com")
                .build();
        changePasswordDto = UserChangePasswordDto.builder()
                .oldPassword("qwerty123")
                .newPassword("1qaz2wsx3edc")
                .build();
    }

    @Test
    void whenFindByIdExpectUserWithThatId() {
        var optionalUserDto = userService.findById(FIND_ID);
        assertTrue(optionalUserDto.isPresent());
        assertEquals(optionalUserDto.get().getId(), rightUser.getId());
        assertEquals(optionalUserDto.get().getName(), rightUser.getName());
        assertEquals(optionalUserDto.get().getLastName(), rightUser.getLastName());
        assertEquals(optionalUserDto.get().getEmail(), rightUser.getEmail());
    }

    @Test
    void whenFindAllExpect3Users() {
        var users = userService.findAll();
        assertThat(users).hasSize(DEFAULT_SIZE);
        var emails = users.stream().map(UserReadDto::getEmail).collect(Collectors.toList());
        assertThat(emails).containsExactlyInAnyOrder("maxik@gmail.com", "katya@gmail.com", "ivan@gmail.com");
    }

    @Test
    void whenFindAllExpectPageSizeAmountUsers() {
        assertThat(userService.findAll(Pageable.ofSize(PAGE_EXPECT_SIZE))).hasSize(PAGE_EXPECT_SIZE);
    }

    @Test
    void whenFindByEmailExpectUserWithThatEmail() {
        var userOpt = userService.findByEmail(EMAIL);
        assertTrue(userOpt.isPresent());
        assertEquals(userOpt.get().getEmail(), EMAIL);
    }

    @Test
    void whenCreateExpectIncreaseFullSizeBy1() {
        assertThat(userService.findAll()).hasSize(DEFAULT_SIZE);
        assertTrue(userService.create(createUserDto).isPresent());
        assertThat(userService.findAll()).hasSize(CREATED_SIZE);
    }

    @Test
    void whenUpdateExpectNewFieldsValues() {
        userService.findById(FIND_ID).ifPresent(
                user -> assertEquals(user.getEmail(), "maxik@gmail.com"));
        var returnOpt = userService.update(FIND_ID, userUpdateDto);
        var dbOpt = userService.findById(FIND_ID);
        assertTrue(returnOpt.isPresent());
        assertTrue(dbOpt.isPresent());
        assertEquals(returnOpt.get().getId(), dbOpt.get().getId());
        assertEquals(returnOpt.get().getName(), dbOpt.get().getName());
        assertEquals(returnOpt.get().getLastName(), dbOpt.get().getLastName());
        assertEquals(returnOpt.get().getEmail(), dbOpt.get().getEmail());
    }

    @Test
    void whenRemoveExpectReduceFullSizeBy1() {
        assertThat(userService.findAll()).hasSize(DEFAULT_SIZE);
        userService.remove(FIND_ID);
        assertFalse(userRepository.existsById(FIND_ID));
        assertThat(userService.findAll()).hasSize(REMOVED_SIZE);
        assertThat(userService.findById(FIND_ID)).isEmpty();
    }

    @Test
    void whenUpdateRoleExpectNewRoleAtUser() {
        assertNotEquals(rightUser.getRole().getRole(), updatedRole.getName());
        userService.updateRole(FIND_ID, updatedRole);
        assertEquals(rightUser.getRole().getRole(), updatedRole.getName());
    }

    @Test
    void whenChangePasswordExpectNewPasswordAtUser() {
        var userOpt = userRepository.findById(FIND_ID);
        assertTrue(userOpt.isPresent());
        userService.changePassword(FIND_ID, changePasswordDto);
    }
}
