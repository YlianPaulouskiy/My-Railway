package by.itacademy.railway.controller;

import by.itacademy.railway.dto.menu.SearchDto;
import by.itacademy.railway.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trains")
public class TrainController {

    private final TrainService trainService;

    @PostMapping("/search")
    public String findByRoute(Model model, SearchDto searchDto) {
        model.addAttribute("trains", trainService.findByRoute(searchDto));
        return "search-result";
    }

}
