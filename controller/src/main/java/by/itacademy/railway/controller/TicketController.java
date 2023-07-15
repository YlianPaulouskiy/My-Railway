package by.itacademy.railway.controller;

import by.itacademy.railway.dto.ticket.TicketCreateEditDto;
import by.itacademy.railway.dto.ticket.TicketIdDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final RouteStationService routeStationService;
    private final PassengerService passengerService;
    private final TicketService ticketService;
    private final SeatService seatService;
    private final OrderService orderService;

    @GetMapping("/new/{trainId}")
    public String pageNewTicket(@SessionAttribute UserReadDto user, @PathVariable Long trainId,
                            @RequestParam String from, String to, Model model) {
        routeStationService.findByTrainIdAndStation(trainId, from)
                .ifPresent(station -> model.addAttribute("from", station));
        routeStationService.findByTrainIdAndStation(trainId, to)
                .ifPresent(station -> model.addAttribute("to", station));
        model.addAttribute("seats", seatService.findByTrainId(trainId));
        model.addAttribute("passengers", passengerService.findAllByUserId(user.getId()));
        return "new-ticket";
    }

    @PostMapping("/create")
    public String create(@SessionAttribute UserReadDto user, TicketIdDto idDto) {
        TicketCreateEditDto createEditDto = TicketCreateEditDto.builder()
                .from(routeStationService.findById(idDto.getFromId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                ))
                .to(routeStationService.findById(idDto.getToId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                ))
                .seat(seatService.findById(idDto.getSeatId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                ))
                .passenger(passengerService.findById(idDto.getPassengerId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                ))
                .order(orderService.create(user.getId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                ))
                .build();
        ticketService.create(createEditDto).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_MODIFIED)
        );
        return "redirect:/main";
    }

}
