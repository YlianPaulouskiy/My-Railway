package by.itacademy.railway.controller;

import by.itacademy.railway.dto.user.UserChangePasswordDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserUpdateDto;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@SessionAttributes("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public String userPage() {
        return "user";
    }

    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable Long userId, @Validated UserUpdateDto updateDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user";
        }
        userService.update(userId, updateDto)
                .ifPresent(user -> model.addAttribute("user", user));
        return "redirect:/users/profile";
    }

    @PostMapping("/remove/{userId}")
    public String removeUser(@PathVariable Long userId) {
        userService.remove(userId);
        return "redirect:/logout";
    }

    @PostMapping("/password/change")
    public String changePassword(UserChangePasswordDto changeDto,
                                 @SessionAttribute UserReadDto user) {
        userService.changePassword(user.getId(), changeDto);
        return "redirect:/users/profile";
    }

}
