package by.itacademy.railway.dto.train;


import by.itacademy.railway.dto.station.route.RouteStationReadDto;
import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
public class TrainSearchDto {

    Long id;
    String code;
    String type;
    RouteStationReadDto from;
    RouteStationReadDto to;
    Integer seatsCount;

}
