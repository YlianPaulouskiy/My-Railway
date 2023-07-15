package by.itacademy.railway.controller;

import by.itacademy.railway.dto.pageable.PageResponse;
import by.itacademy.railway.dto.role.RoleIdDto;
import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import by.itacademy.railway.service.OrderService;
import by.itacademy.railway.service.PassengerService;
import by.itacademy.railway.service.RoleService;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;
    private final PassengerService passengerService;
    private final RoleService roleService;

    @GetMapping("/users")
    public String findAll(Model model, Pageable pageable) {
        model.addAttribute("users", PageResponse.of(userService.findAll(pageable)));
        model.addAttribute("roles", roleService.findAll());
        return "users";
    }

    @PostMapping("/update/role/{userId}")
    public String updateRole(@PathVariable Long userId, RoleIdDto updateRole) {
        userService.updateRole(userId, roleService.findById(updateRole.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return "redirect:/admin/users";
    }

    @PostMapping("/remove/user/{userId}")
    public String removeUser(@PathVariable Long userId) {
        userService.remove(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/orders/{userId}")
    public String userOrders(@PathVariable Long userId, Model model,
                             @PageableDefault(value = 3) Pageable pageable) {
        model.addAttribute("orders",
                PageResponse.of(orderService.findAllByUserId(userId, pageable)));
        return "orders";
    }

    @GetMapping("/passengers/{userId}")
    public String userPassengers(@PathVariable Long userId, Model model,
                             @PageableDefault(value = 3) Pageable pageable) {
        model.addAttribute("passengers",
                PageResponse.of(passengerService.findAllByUserId(userId, pageable)));
        model.addAttribute("genders", Gender.values());
        model.addAttribute("documents", DocumentType.values());
        return "passengers";
    }



}
