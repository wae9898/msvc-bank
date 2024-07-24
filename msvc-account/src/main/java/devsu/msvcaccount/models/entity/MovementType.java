package devsu.msvcaccount.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MOVEMENT_TYPES")
public class MovementType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOVEMENT_TYPE_ID", unique = true, nullable = false)
    private Long id;

    private String name;
}
