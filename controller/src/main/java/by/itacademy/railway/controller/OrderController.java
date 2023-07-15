package by.itacademy.railway.controller;

import by.itacademy.railway.dto.pageable.PageResponse;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String findByUserId(@SessionAttribute UserReadDto user, Model model,
                               @PageableDefault(value = 2) Pageable pageable) {
        model.addAttribute("orders",
                PageResponse.of(orderService.findAllByUserId(user.getId(), pageable)));
        return "orders";
    }

}
