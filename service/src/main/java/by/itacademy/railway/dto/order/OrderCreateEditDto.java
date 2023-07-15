package by.itacademy.railway.dto.order;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class OrderCreateEditDto {

    Long id;
    String no;
    String status;
    LocalDateTime registrationTime;
    LocalDateTime payedTime;

}
