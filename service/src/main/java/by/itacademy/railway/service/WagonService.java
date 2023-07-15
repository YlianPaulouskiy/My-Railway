package by.itacademy.railway.service;

import by.itacademy.railway.dto.wagon.WagonReadDto;
import by.itacademy.railway.mapper.wagon.WagonReadMapper;
import by.itacademy.railway.repository.WagonRepository;
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
public class WagonService {

    private final WagonRepository wagonRepository;
    private final WagonReadMapper wagonReadMapper;

    @Transactional(readOnly = true)
    public List<WagonReadDto> findByTrainId(@NotNull(message = "Train id can't be null.") Long trainId) {
        return wagonRepository.findAllByTrainId(trainId).stream()
                .map(wagonReadMapper::toDto).collect(Collectors.toList());
    }

}
