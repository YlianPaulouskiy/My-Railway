package by.itacademy.railway.entity;


import by.itacademy.railway.entity.embedded.OrderInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders", schema = "public")
public class Order implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "no", column = @Column(nullable = false, unique = true)),
            @AttributeOverride(name = "status", column = @Column(nullable = false)),
            @AttributeOverride(name = "registrationTime", column = @Column(name = "registration_time", nullable = false,
                    columnDefinition = "timestamp default now()")),
            @AttributeOverride(name = "payedTime", column = @Column(name = "payed_time"))
    })
    private OrderInfo orderInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "order")
    private List<Ticket> tickets = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        this.user.getOrders().add(this);
    }
}
