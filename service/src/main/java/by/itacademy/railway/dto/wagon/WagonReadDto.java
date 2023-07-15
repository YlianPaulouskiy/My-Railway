package by.itacademy.railway.dto.wagon;

import by.itacademy.railway.dto.train.TrainReadDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WagonReadDto {

    Long id;
    Short no;
    String type;
    TrainReadDto train;

}
