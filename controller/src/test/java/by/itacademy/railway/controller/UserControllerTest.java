package by.itacademy.railway.controller;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.passenger.PassengerCreateEditDto;
import by.itacademy.railway.dto.user.UserChangePasswordDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserUpdateDto;
import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserControllerTest {

    private final MockMvc mockMvc;
    private final UserService userService;
    private UserReadDto user;

    @BeforeEach
    void setUp() {
        userService.findById(1L).ifPresent(userReadDto -> user = userReadDto);
    }


    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectSuccessfulWhenComeUserPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile")
        .sessionAttr("user", user))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectRedirectWhenUpdateUser() throws Exception {
        mockMvc.perform(post("/users/update/1")
                .param(UserUpdateDto.Fields.name, "Name")
                .param(UserUpdateDto.Fields.lastName, "LastName")
                .param(UserUpdateDto.Fields.email, "exmple@gmail.com"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/profile"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectRedirectWhenRemoveUser() throws Exception {
        mockMvc.perform(post("/users/remove/1"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/logout"));
    }

}
