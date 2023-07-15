package by.itacademy.railway.controller;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.role.RoleIdDto;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
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
public class AdminControllerTest {

    private final MockMvc mockMvc;

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"ADMIN"})
    void expectSuccessfulWhenComeToUsersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("users", "roles"))
                .andExpect(view().name("users"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"ADMIN"})
    void expectSuccessfulWhenComeToOrdersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("orders"))
                .andExpect(view().name("orders"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"ADMIN"})
    void expectSuccessfulWhenComeToPassengersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/passengers/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("passengers", "genders", "documents"))
                .andExpect(view().name("passengers"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"ADMIN"})
    void expectRedirectWhenUpdateUserRole() throws Exception {
        mockMvc.perform(post("/admin/update/role/2")
                .param(RoleIdDto.Fields.roleId, "3"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/users"));
    }

    @Test
    @WithMockUser(username = "maxik@gmail.com", password = "qwerty123", authorities = {"ADMIN"})
    void expectRedirectWhenRemoveUser() throws Exception {
        mockMvc.perform(post("/admin/remove/user/2"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/users"));
    }


}
