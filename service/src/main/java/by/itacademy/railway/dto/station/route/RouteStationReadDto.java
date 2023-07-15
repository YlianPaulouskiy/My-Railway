package by.itacademy.railway.dto.station.route;

import by.itacademy.railway.dto.station.StationReadDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class RouteStationReadDto {

    Long id;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    StationReadDto station;

}
