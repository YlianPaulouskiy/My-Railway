package by.itacademy.railway.entity;


import by.itacademy.railway.entity.embedded.TrainInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "trains", schema = "public")
public class Train implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "code", column = @Column(nullable = false, unique = true)),
            @AttributeOverride(name = "type", column = @Column(nullable = false))
    })
    private TrainInfo trainInfo;

    @Builder.Default
    @OneToMany(mappedBy = "train")
    private List<RouteStation> routeStations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "train")
    private List<Wagon> wagons = new ArrayList<>();

}
