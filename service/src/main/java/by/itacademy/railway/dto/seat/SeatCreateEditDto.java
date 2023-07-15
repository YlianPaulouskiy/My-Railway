package by.itacademy.railway.dto.seat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SeatCreateEditDto {

    Long id;
    Short no;
    Double cost;
    String type;

}
