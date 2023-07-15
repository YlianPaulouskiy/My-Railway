package by.itacademy.railway.dto.ticket;

import by.itacademy.railway.dto.order.OrderCreateEditDto;
import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.dto.seat.SeatCreateEditDto;
import by.itacademy.railway.dto.station.route.RouteStationReadDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TicketCreateEditDto {

    RouteStationReadDto from;
    RouteStationReadDto to;
    PassengerReadDto passenger;
    SeatCreateEditDto seat;
    OrderCreateEditDto order;

}
