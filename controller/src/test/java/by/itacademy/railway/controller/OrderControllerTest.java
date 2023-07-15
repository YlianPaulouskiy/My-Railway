package by.itacademy.railway.controller;


import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class OrderControllerTest {

    private final MockMvc mockMvc;
    private final UserService userService;
    private UserReadDto user;

    @BeforeEach
    void setUp() {
        userService.findById(1L).ifPresent(userReadDto -> user = userReadDto);
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectSuccessfulWhenComeToOrdersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .sessionAttr("user", user))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("orders"));
    }






}
