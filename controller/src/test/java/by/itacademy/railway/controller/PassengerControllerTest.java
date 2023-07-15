package by.itacademy.railway.controller;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.passenger.PassengerCreateEditDto;
import by.itacademy.railway.dto.user.UserReadDto;
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
public class PassengerControllerTest {

    private final MockMvc mockMvc;
    private final UserService userService;
    private UserReadDto user;

    @BeforeEach
    void setUp() {
        userService.findById(1L).ifPresent(userReadDto -> user = userReadDto);
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectSuccessfulWhenFindPassengersByUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/passengers")
                .sessionAttr("user", user))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("passengers", "genders", "documents"))
                .andExpect(view().name("passengers"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectSuccessfulWhenComeCreatePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/passengers/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("genders", "documents"))
                .andExpect(view().name("new-passenger"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectRedirectWhenRemovePassenger() throws Exception {
        mockMvc.perform(post("/passengers/remove/2"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/passengers"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectRedirectWhenCreatePassenger() throws Exception {
        mockMvc.perform(post("/passengers/create")
                .sessionAttr("user", user)
        .param(PassengerCreateEditDto.Fields.name, "Name")
        .param(PassengerCreateEditDto.Fields.lastName, "LastName")
        .param(PassengerCreateEditDto.Fields.middleName, "Midname")
        .param(PassengerCreateEditDto.Fields.gender, String.valueOf(Gender.MALE))
        .param(PassengerCreateEditDto.Fields.document, String.valueOf(DocumentType.DRIVER_LICENSE))
        .param(PassengerCreateEditDto.Fields.documentNo, "123212321"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/passengers"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectRedirectWhenUpdatePassenger() throws Exception {
        mockMvc.perform(post("/passengers/update/1")
                .sessionAttr("user", user)
        .param(PassengerCreateEditDto.Fields.name, "Name")
        .param(PassengerCreateEditDto.Fields.lastName, "LastName")
        .param(PassengerCreateEditDto.Fields.middleName, "Midname")
        .param(PassengerCreateEditDto.Fields.gender, String.valueOf(Gender.MALE))
        .param(PassengerCreateEditDto.Fields.document, String.valueOf(DocumentType.DRIVER_LICENSE))
        .param(PassengerCreateEditDto.Fields.documentNo, "123212321"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/passengers"));
    }



}
