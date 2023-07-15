package by.itacademy.railway.service;

import by.itacademy.railway.dto.station.route.RouteStationReadDto;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.mapper.station.route.RouteStationReadMapper;
import by.itacademy.railway.repository.RouteStationRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class RouteStationService {

    private final RouteStationRepository routeStationRepository;
    private final RouteStationReadMapper routeStationReadMapper;
    private final TicketService ticketService;

    @Transactional(readOnly = true)
    public Optional<RouteStationReadDto> findByTrainIdAndStation(@NotNull(message = "Train id can't be null.") Long trainId,
                                                                 @NotBlank(message = "Station name is required.") String station) {
        return routeStationRepository.findByTrainIdAndStationName(trainId, station).map(routeStationReadMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RouteStationReadDto> findById(@NotNull(message = "Route-Station id can't be null.") Long id) {
        return routeStationRepository.findById(id).map(routeStationReadMapper::toDto);
    }

    @Transactional
    public void remove(@NotNull(message = "RouteStation id can't be null.") Long id) {
        deleteTicketsWhichLinkedAtRouteStation(id);
        routeStationRepository.deleteById(id);
    }

    /**
     * Удаляет билеты которые ссылаются на этот пункт маршрута
     *
     * @param id идентификационный номер пункта маршрута
     */
    private void deleteTicketsWhichLinkedAtRouteStation(Long id) {
        routeStationRepository.findById(id).ifPresent(routeStation -> {
            routeStation.getFromTickets().stream().map(Ticket::getId).forEach(ticketService::remove);
            routeStation.getToTickets().stream().map(Ticket::getId).forEach(ticketService::remove);
        });
    }

}
