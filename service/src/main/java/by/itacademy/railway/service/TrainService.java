package by.itacademy.railway.service;

import by.itacademy.railway.dto.menu.SearchDto;
import by.itacademy.railway.dto.train.TrainSearchDto;
import by.itacademy.railway.entity.RouteStation;
import by.itacademy.railway.entity.Train;
import by.itacademy.railway.mapper.train.TrainSearchMapper;
import by.itacademy.railway.repository.TrainRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final RouteStationService routeStationService;
    private final TrainSearchMapper trainSearchMapper;

    @Transactional
    public List<TrainSearchDto> findByRoute(@Valid SearchDto searchDto) {
        List<TrainSearchDto> trainSearchList = new ArrayList<>();
        for (Train train : trainRepository.findAll()) {
            var stations = train.getRouteStations();
            checkCoincidenceByDepartureStation(searchDto, trainSearchList, train, stations);
        }
        return trainSearchList;
    }

    /**
     * Сверяет в найденном поезде станцию отправления,
     * если подходит сверяет станцию прибытия
     */
    private void checkCoincidenceByDepartureStation(SearchDto searchDto, List<TrainSearchDto> trainSearchList,
                                                    Train train, List<RouteStation> stations) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getStation().getName().equals(searchDto.getFrom())) { //совпадение станции отправления
                checkCoincidenceByArrivalStation(searchDto, trainSearchList, train, stations, i);
                break; // если нашли станцию отправления, и не нашли станцию прибытия проверять остальные смысла нет
            }
        }
    }

    /**
     * Сверяет в найденном поезде станцию прибытия,
     * если подходит сверяет время
     */
    private void checkCoincidenceByArrivalStation(SearchDto searchDto, List<TrainSearchDto> trainSearchList,
                                                  Train train, List<RouteStation> stations, int i) {
        for (int j = i + 1; j < stations.size(); j++) {
            if (stations.get(j).getStation().getName().equals(searchDto.getTo())) { //совпадение станции прибытия
                checkCoincidenceByTimes(searchDto, trainSearchList, train, stations, i);
            }
        }
    }

    /**
     * Проверяет только станцию отправления
     * если подходит добавляет поезд в список
     */
    private void checkCoincidenceByTimes(SearchDto searchDto, List<TrainSearchDto> trainSearchList, Train train, List<RouteStation> stations, int i) {
        if (!trainSearchList.contains(getSearchTrain(train, searchDto.getFrom(), searchDto.getTo()))
            && stations.get(i).getDepartureTime() != null
            && stations.get(i).getDepartureTime().toLocalDate().isEqual(searchDto.getWhen())
            && stations.get(i).getDepartureTime().toLocalTime()
                    .isAfter(LocalDateTime.of(searchDto.getWhen(), LocalTime.MIN).toLocalTime())) {
            trainSearchList.add(getSearchTrain(train, searchDto.getFrom(), searchDto.getTo()));
        }
    }

    /**
     * Получает нужный поезд
     */
    private TrainSearchDto getSearchTrain(Train train, String from, String to) {
        return trainSearchMapper.toDto(train,
                routeStationService.findByTrainIdAndStation(train.getId(), from)
                        .orElseThrow(() -> {
                            throw new EntityNotFoundException("RouteStation by train id = " + train.getId()
                                                              + " and station name = " + from + " not found!!!");
                        }),
                routeStationService.findByTrainIdAndStation(train.getId(), to)
                        .orElseThrow(() -> {
                            throw new EntityNotFoundException("RouteStation by train id = " + train.getId()
                                                              + " and station name = " + to + " not found!!!");
                        }));
    }

}
