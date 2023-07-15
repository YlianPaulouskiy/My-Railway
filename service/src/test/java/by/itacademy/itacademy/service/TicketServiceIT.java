package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.dto.ticket.TicketCreateEditDto;
import by.itacademy.railway.repository.TicketRepository;
import by.itacademy.railway.service.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class TicketServiceIT {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    private final RouteStationService routeStationService;
    private final PassengerService passengerService;
    private final SeatService seatService;
    private final OrderService orderService;
    private final static Long USER_ID = 1L;
    private final static Integer DEFAULT_SIZE = 15;
    private final static Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private final static Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private final static Long TICKET_ID = 1L;
    private TicketCreateEditDto createEditDto;

    @BeforeEach
    void setUp() {
        createEditDto = TicketCreateEditDto.builder()
                .from(routeStationService.findByTrainIdAndStation(1L, "MINSK").orElseThrow())
                .to(routeStationService.findByTrainIdAndStation(1L, "GRODNO").orElseThrow())
                .passenger(passengerService.findAllByUserId(1L).get(0))
                .seat(seatService.findByWagonId(1L).get(0))
                .order(orderService.create(USER_ID).orElseThrow())
                .build();
    }

    @Test
    void whenCreateExpectIncreaseFullSizeBy1() {
        assertThat(ticketRepository.findAll()).hasSize(DEFAULT_SIZE);
        var ticketOpt = ticketService.create(createEditDto);
        assertTrue(ticketOpt.isPresent());
        assertEquals(ticketOpt.get().getFrom(), createEditDto.getFrom());
        assertEquals(ticketOpt.get().getTo(), createEditDto.getTo());
        assertEquals(ticketOpt.get().getPassenger(), createEditDto.getPassenger());
        assertThat(ticketRepository.findAll()).hasSize(CREATED_SIZE);
    }

    @Test
    void whenRemoveExpectReduceFullSizeBy1() {
        assertThat(ticketRepository.findAll()).hasSize(DEFAULT_SIZE);
        ticketService.remove(TICKET_ID);
        assertThat(ticketRepository.existsById(TICKET_ID)).isFalse();
        assertThat(ticketRepository.findAll()).hasSize(REMOVED_SIZE);
    }
}
