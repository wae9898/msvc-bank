package devsu.msvccustomer.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "PERSONS")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID", unique = true, nullable = false)
    private Long id;

    @NotEmpty
    @Size(min=3)
    private String name;

    @NotEmpty
    private String gender;

    @NotNull
    private Integer age;

    @NotEmpty
    @Size(min=13)
    private String identification;

    @NotEmpty
    @Size(min=5)
    private String address;

    @NotEmpty
    @Size(min=8)
    private String phone;
}
