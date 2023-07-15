package by.itacademy.railway.dto.station;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StationReadDto {

    Integer id;
    String name;

}


