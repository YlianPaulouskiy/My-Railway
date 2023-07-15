package by.itacademy.railway.entity;


import by.itacademy.railway.entity.embedded.SeatInfo;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = {"id", "ticket"})
@Table(name = "seats", schema = "public")
public class Seat implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverrides({
            @AttributeOverride(name = "no", column = @Column(nullable = false)),
            @AttributeOverride(name = "type", column = @Column(nullable = false))
    })
    private SeatInfo seatInfo;

    @Column(nullable = false)
    private Double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "wagon_id", referencedColumnName = "id")
    private Wagon wagon;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "ticket_id", referencedColumnName = "id", unique = true)
    private Ticket ticket;

    public void setWagon(Wagon wagon) {
        this.wagon = wagon;
        this.wagon.getSeats().add(this);
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        if (this.ticket != null) {
            this.ticket.setSeat(this);
        }
    }
}
