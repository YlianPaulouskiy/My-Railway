package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.dto.menu.SearchDto;
import by.itacademy.railway.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
@RequiredArgsConstructor
public class TrainServiceIT {

    private final TrainService trainService;
    private final static Integer DEFAULT_SIZE = 2;
    private SearchDto searchDto;

    @BeforeEach
    void setUp() {
        searchDto = SearchDto.builder()
                .from("MINSK").to("OSIPOVICHI").when(LocalDate.parse("2023-08-07")).build();
    }

    @Test
    void whenFindByRouteExpectTrainWithThisIdAndStations() {
        var trains = trainService.findByRoute(searchDto);
        assertThat(trains).hasSize(DEFAULT_SIZE);
        trains.forEach(train -> {
            assertEquals(train.getFrom().getStation().getName(), searchDto.getFrom());
            assertEquals(train.getTo().getStation().getName(), searchDto.getTo());
            assertEquals(train.getFrom().getDepartureTime().toLocalDate(), searchDto.getWhen());
        });
    }
}
