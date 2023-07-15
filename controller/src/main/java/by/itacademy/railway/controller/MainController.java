package by.itacademy.railway.controller;

import by.itacademy.railway.dto.user.UserCreateEditDto;
import by.itacademy.railway.service.StationService;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping
@SessionAttributes("user")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final StationService stationService;

    @GetMapping("/main")
    public String pageMain(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (model.getAttribute("user") == null && userDetails != null) {
            model.addAttribute("user",
                    userService.findByEmail(userDetails.getUsername())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        }
        model.addAttribute("stations", stationService.findAll());
        return "main";
    }

    @GetMapping("/login")
    public String pageLogin() {
        return "login-page";
    }

    @GetMapping("/registration")
    public String pageRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Validated UserCreateEditDto createEditDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration";
        }
        userService.create(createEditDto);
        return "redirect:/login";
    }

}
