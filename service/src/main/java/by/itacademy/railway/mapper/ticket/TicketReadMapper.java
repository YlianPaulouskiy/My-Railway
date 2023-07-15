package by.itacademy.railway.mapper.ticket;

import by.itacademy.railway.dto.ticket.TicketReadDto;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.mapper.CommonMapper;
import by.itacademy.railway.mapper.passenger.PassengerReadMapper;
import by.itacademy.railway.mapper.seat.SeatReadMapper;
import by.itacademy.railway.mapper.station.route.RouteStationReadMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RouteStationReadMapper.class,
        PassengerReadMapper.class, SeatReadMapper.class})
public interface TicketReadMapper extends CommonMapper<Ticket, TicketReadDto> {

}
