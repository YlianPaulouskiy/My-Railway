package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.mapper.wagon.WagonReadMapper;
import by.itacademy.railway.service.WagonService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class WagonServiceIT {

    private final WagonService wagonService;
    private final static Long TRAIN_ID = 1L;
    private final static Integer DEFAULT_SIZE = 3;

    @Test
    void whenFindByTrainIdExpect3Wagons() {
        assertThat(wagonService.findByTrainId(TRAIN_ID)).hasSize(DEFAULT_SIZE);
    }

}
