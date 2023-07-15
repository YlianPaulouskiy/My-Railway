package by.itacademy.railway.dto.ticket;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Builder
@Value
@FieldNameConstants
public class TicketIdDto {

    Long fromId;
    Long toId;
    Long seatId;
    Long passengerId;

}
