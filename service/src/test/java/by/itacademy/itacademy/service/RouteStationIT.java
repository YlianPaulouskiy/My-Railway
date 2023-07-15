package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.repository.RouteStationRepository;
import by.itacademy.railway.service.RouteStationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class RouteStationIT {

    private final RouteStationService routeStationService;
    private final RouteStationRepository routeStationRepository;
    private final static Integer DEFAULT_SIZE = 51;
    private final static Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private final static Long TRAIN_ID = 1L;
    private final static Long ROUTE_STATION_ID = 1L;
    private final static String STATION_NAME = "MINSK";

    @Test
    void whenFindByTrainIdAndStationExpectRouteStationWithThatTrainIdAndStation() {
        var routeSt = routeStationService.findByTrainIdAndStation(TRAIN_ID, STATION_NAME);
        assertTrue(routeSt.isPresent());
        routeStationRepository.findByTrainIdAndStationName(TRAIN_ID, STATION_NAME)
                .ifPresent(routeStation -> assertEquals(routeStation.getTrain().getId(), TRAIN_ID));
        assertEquals(routeSt.get().getStation().getName(), STATION_NAME);
    }

    @Test
    void whenRemoveExpectReduceFullSizeBy1() {
        assertThat(routeStationRepository.findAll()).hasSize(DEFAULT_SIZE);
        routeStationService.remove(ROUTE_STATION_ID);
        assertFalse(routeStationRepository.existsById(ROUTE_STATION_ID));
        assertThat(routeStationRepository.findAll()).hasSize(REMOVED_SIZE);
    }

}
