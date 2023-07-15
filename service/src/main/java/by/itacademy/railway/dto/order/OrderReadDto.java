package by.itacademy.railway.dto.order;

import by.itacademy.railway.dto.ticket.TicketReadDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class OrderReadDto {

    Long id;
    String no;
    String status;
    LocalDateTime registrationTime;
    LocalDateTime payedTime;
    Double cost;
    List<TicketReadDto> tickets;

}
