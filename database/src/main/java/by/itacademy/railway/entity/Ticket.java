package by.itacademy.railway.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = "id")
@Table(name = "tickets", schema = "public")
public class Ticket implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private RouteStation from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    private RouteStation to;

    @OneToOne(mappedBy = "ticket")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
        this.order.getTickets().add(this);
    }
}
