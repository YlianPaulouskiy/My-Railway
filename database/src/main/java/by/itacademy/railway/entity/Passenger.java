package by.itacademy.railway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "passengers", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"document", "document_no"}))
public class Passenger implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DocumentType document;

    @Column(name = "document_no", nullable = false)
    private String documentNo;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<UserPassenger> users = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "passenger")
    private List<Ticket> tickets = new ArrayList<>();

}
