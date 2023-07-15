package by.itacademy.railway.entity.embedded;

import by.itacademy.railway.entity.TrainType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class TrainInfo {

    private String code;

    @Enumerated(value = EnumType.STRING)
    private TrainType type;

}
