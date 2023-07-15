package by.itacademy.railway.repository;

import by.itacademy.railway.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class RouteStationRepositoryTest {

    private final RouteStationRepository routeStationRepository;
    private final static Long TRAIN_ID = 1L;
    private final static String STATION_NAME = "MINSK";

    @Test
    void whenFindByTrainIdAndStationNameExpectRouteStationWithThatTrainAndStation() {
        var routeStationOpt = routeStationRepository.findByTrainIdAndStationName(TRAIN_ID, STATION_NAME);
        assertTrue(routeStationOpt.isPresent());
        assertEquals(routeStationOpt.get().getTrain().getId(), TRAIN_ID);
        assertEquals(routeStationOpt.get().getStation().getName(), STATION_NAME);
    }
}
