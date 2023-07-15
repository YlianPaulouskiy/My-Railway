package by.itacademy.railway.service;

import by.itacademy.railway.dto.station.StationReadDto;
import by.itacademy.railway.entity.RouteStation;
import by.itacademy.railway.mapper.station.StationReadMapper;
import by.itacademy.railway.repository.StationRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final RouteStationService routeStationService;
    private final StationReadMapper stationReadMapper;

    @Transactional(readOnly = true)
    public List<StationReadDto> findAll() {
        return stationRepository.findAll().stream()
                .map(stationReadMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void remove(@NotNull(message = "Station id can't be null") Integer id) {
        deleteRouteStationWhichLinkedAtStation(id);
        stationRepository.deleteById(id);
    }

    /**
     * Удаляет пункты маршрута которые ссылаются на текущую станцию
     *
     * @param id идентификационный номер станции
     */
    private void deleteRouteStationWhichLinkedAtStation(Integer id) {
        stationRepository.findById(id).ifPresent(station -> station.getRouteStations()
                .stream()
                .map(RouteStation::getId)
                .forEach(routeStationService::remove));
    }

}
