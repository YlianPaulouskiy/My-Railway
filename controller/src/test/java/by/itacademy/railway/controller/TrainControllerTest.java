package by.itacademy.railway.controller;


import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.menu.SearchDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class TrainControllerTest {

    private final MockMvc mockMvc;

    @Test
    void expectSuccessfulWhenSearchTrains() throws Exception {
        mockMvc.perform(post("/trains/search")
        .param(SearchDto.Fields.from, "MINSK")
        .param(SearchDto.Fields.to, "GRODNO")
        .param(SearchDto.Fields.when, String.valueOf(LocalDate.of(2023,8,7)))
        )
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("trains"),
                        view().name("search-result"));
    }

}
