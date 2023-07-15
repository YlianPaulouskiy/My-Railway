package by.itacademy.railway.repository;

import by.itacademy.railway.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class SeatRepositoryTest {

    private final SeatRepository seatRepository;
    private final static Long TRAIN_ID = 1L;
    private final static Integer TRAIN_SEATS = 27;
    private final static Long WAGON_ID = 3L;
    private final static Integer WAGON_SEATS = 10;

    @Test
    void whenFindAllByTrainIdExpect27Seats() {
        assertThat(seatRepository.findAllByTrainId(TRAIN_ID)).hasSize(TRAIN_SEATS);
    }

    @Test
    void whenFindAllByWagonIdExpect10Seats() {
        assertThat(seatRepository.findAllByWagonId(WAGON_ID)).hasSize(WAGON_SEATS);
    }


}
