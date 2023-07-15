package by.itacademy.railway.entity.embedded;

import by.itacademy.railway.entity.OrderStatus;
import by.itacademy.railway.entity.converter.TimeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class OrderInfo {

    private String no;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Convert(converter = TimeConverter.class)
    private LocalDateTime registrationTime;

    @Convert(converter = TimeConverter.class)
    private LocalDateTime payedTime;

}
