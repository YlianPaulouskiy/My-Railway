package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class SeatServiceIT {

    private final SeatService seatService;
    private final static Long WAGON_ID = 1L;
    private final static Long TRAIN_ID = 1L;
    private final static Integer TRAIN_DEFAULT_SIZE = 27;
    private final static Integer WAGON_DEFAULT_SIZE = 10;

    @Test
    void whenFindByWagonIdExpect10Seats() {
        assertThat(seatService.findByWagonId(WAGON_ID)).hasSize(WAGON_DEFAULT_SIZE);
    }
    @Test
    void whenFindByTrainIdExpect27Seats() {
        assertThat(seatService.findByTrainId(TRAIN_ID)).hasSize(TRAIN_DEFAULT_SIZE);
    }
}
