package by.itacademy.railway.controller;

import by.itacademy.railway.dto.pageable.PageResponse;
import by.itacademy.railway.dto.passenger.PassengerCreateEditDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import by.itacademy.railway.service.PassengerService;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengersController {

    private final PassengerService passengerService;

    @GetMapping
    public String findByUserId(@SessionAttribute UserReadDto user, Model model,
                               @PageableDefault(value = 5) Pageable pageable) {
        model.addAttribute("passengers",
                PageResponse.of(passengerService.findAllByUserId(user.getId(), pageable)));
        model.addAttribute("genders", Gender.values());
        model.addAttribute("documents", DocumentType.values());
        return "passengers";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {
        passengerService.remove(id);
        return "redirect:/passengers";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, PassengerCreateEditDto createEditDto) {
        passengerService.update(id, createEditDto);
        return "redirect:/passengers";
    }

    @PostMapping("/create")
    public String create(@SessionAttribute UserReadDto user, PassengerCreateEditDto createEditDto) {
        passengerService.create(user.getId(), createEditDto);
        return "redirect:/passengers";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("documents", DocumentType.values());
        return "new-passenger";
    }

}
