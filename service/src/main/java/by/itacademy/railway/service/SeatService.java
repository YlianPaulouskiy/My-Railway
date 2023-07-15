package by.itacademy.railway.service;

import by.itacademy.railway.dto.seat.SeatCreateEditDto;
import by.itacademy.railway.dto.seat.SeatReadDto;
import by.itacademy.railway.mapper.seat.SeatCreateEditMapper;
import by.itacademy.railway.mapper.seat.SeatReadMapper;
import by.itacademy.railway.repository.SeatRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final SeatCreateEditMapper seatCreateEditMapper;
    private final SeatReadMapper seatReadMapper;

    @Transactional(readOnly = true)
    public List<SeatCreateEditDto> findByWagonId(@NotNull(message = "Wagon id can't be null.") Long wagonId) {
        return seatRepository.findAllByWagonId(wagonId).stream()
                .map(seatCreateEditMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SeatReadDto> findByTrainId(@NotNull(message = "Train id can't be null.") Long trainId) {
        return seatRepository.findAllByTrainId(trainId).stream()
                .map(seatReadMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<SeatCreateEditDto> findById(@NotNull(message = "Seat id can't be null.") Long id) {
        return seatRepository.findById(id).map(seatCreateEditMapper::toDto);
    }

}
