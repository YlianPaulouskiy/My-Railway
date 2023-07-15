package by.itacademy.railway.controller;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.ticket.TicketIdDto;
import by.itacademy.railway.dto.user.UserReadDto;
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
public class TicketControllerTest {

    private final MockMvc mockMvc;
    private final UserService userService;
    private UserReadDto user;

    @BeforeEach
    void setUp() {
        userService.findById(1L).ifPresent(userReadDto -> user = userReadDto);
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectSuccessfulWhenComeCreateTicketPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/new/1")
                .sessionAttr("user", user)
                .param("from", "MINSK")
                .param("to", "GRODNO"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("passengers", "seats", "from", "to"))
                .andExpect(view().name("new-ticket"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"USER"})
    void expectRedirectWhenCreateTicket() throws Exception {
        mockMvc.perform(post("/tickets/create")
                .sessionAttr("user", user)
        .param(TicketIdDto.Fields.fromId, "1")
        .param(TicketIdDto.Fields.toId, "3")
        .param(TicketIdDto.Fields.seatId, "10")
        .param(TicketIdDto.Fields.passengerId, "3")
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/main"));
    }

}
