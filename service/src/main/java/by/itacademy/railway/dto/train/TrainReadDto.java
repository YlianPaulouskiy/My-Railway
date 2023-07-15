package by.itacademy.railway.dto.train;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TrainReadDto {

    Long id;
    String code;
    String type;

}
