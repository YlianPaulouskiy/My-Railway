package by.itacademy.railway.controller;

import by.itacademy.railway.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static by.itacademy.railway.dto.user.UserCreateEditDto.Fields.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class MainControllerTest {

    private final MockMvc mockMvc;

    @Test
    void expectSuccessfulWhenComeToMainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/main"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("stations"))
                .andExpect(view().name("main"));
    }

    @Test
    void expectSuccessfulWhenComeToLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login-page"));
    }

    @Test
    void expectRedirectWhenUserRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                .param(name, "Name")
                .param(lastName, "LastName")
                .param(email, "email@gmail.com")
                .param(password, "password123")
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        view().name("redirect:/login"));
    }

    @Test
    void expectSuccessfulWhenComeToRegistrationPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("registration"));
    }


}
