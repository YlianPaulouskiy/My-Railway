package by.itacademy.railway.entity;

import by.itacademy.railway.entity.embedded.WagonInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "wagons", schema = "public")
public class Wagon implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverrides({
            @AttributeOverride(name = "no", column = @Column(nullable = false)),
            @AttributeOverride(name = "type", column = @Column(nullable = false))
    })
    private WagonInfo wagonInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    private Train train;

    @Builder.Default
    @OneToMany(mappedBy = "wagon")
    private List<Seat> seats = new ArrayList<>();

    public void setTrain(Train train) {
        this.train = train;
        this.train.getWagons().add(this);
    }
}
