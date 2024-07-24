package devsu.msvcaccount.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ACCOUNT_TYPES")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_TYPE_ID", unique = true, nullable = false)
    private Long id;

    private String name;

    private String description;

    private Boolean status;
}
