package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.dto.station.StationReadDto;
import by.itacademy.railway.repository.StationRepository;
import by.itacademy.railway.service.StationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class StationServiceIT {

    private final StationService stationService;
    private final StationRepository stationRepository;
    private final static Integer DEFAULT_SIZE = 13;
    private final static Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private final static Integer REMOVE_STATION_ID = 1;

    @Test
    void whenFindAllExpect13Stations() {
        List<StationReadDto> stations = stationService.findAll();
        assertThat(stations).hasSize(DEFAULT_SIZE);
        List<String> stationNames = stations.stream().map(StationReadDto::getName).collect(Collectors.toList());
        assertThat(stationNames).containsAnyOf("MINSK", "GRODNO", "BREST", "PINSK");
    }

    @Test
    void whenRemoveExpectReduceFullSizeBy1() {
        assertThat(stationService.findAll()).hasSize(DEFAULT_SIZE);
        stationService.remove(REMOVE_STATION_ID);
        assertThat(stationRepository.existsById(REMOVE_STATION_ID)).isFalse();
        assertThat(stationService.findAll()).hasSize(REMOVED_SIZE);
    }

}
