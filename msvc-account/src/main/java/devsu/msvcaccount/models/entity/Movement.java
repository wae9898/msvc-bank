package devsu.msvcaccount.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "MOVEMENTS")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOVEMENT_ID", unique = true, nullable = false)
    private Long id;

    private LocalDate date;

    @NotNull
    @Column(name = "MOVEMENT_TYPE_ID")
    private Long movementTypeId;

    @NotNull
    private BigDecimal value;

    @NotNull
    private BigDecimal balance;

    @NotNull
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }
}
