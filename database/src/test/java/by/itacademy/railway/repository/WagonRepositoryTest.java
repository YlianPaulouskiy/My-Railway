package by.itacademy.railway.repository;

import by.itacademy.railway.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class WagonRepositoryTest {

    private final WagonRepository wagonRepository;
    private final static Long TRAIN_ID = 1L;
    private final static Integer TRAIN_WAGONS = 3;

    @Test
    void whenFindAllByTrainIdExpect3Wagons() {
        assertThat(wagonRepository.findAllByTrainId(TRAIN_ID)).hasSize(TRAIN_WAGONS);
    }

}
