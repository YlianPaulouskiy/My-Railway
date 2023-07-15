package by.itacademy.railway.mapper.ticket;

import by.itacademy.railway.dto.ticket.TicketCreateEditDto;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.mapper.CommonMapper;
import by.itacademy.railway.mapper.order.OrderCreateEditMapper;
import by.itacademy.railway.mapper.passenger.PassengerReadMapper;
import by.itacademy.railway.mapper.seat.SeatCreateEditMapper;
import by.itacademy.railway.mapper.station.route.RouteStationReadMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RouteStationReadMapper.class,
        PassengerReadMapper.class, SeatCreateEditMapper.class, OrderCreateEditMapper.class})
public interface TicketCreateEditMapper extends CommonMapper<Ticket, TicketCreateEditDto> {
}
