package devsu.msvccustomer.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.checkerframework.common.aliasing.qual.Unique;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "CUSTOMERS")
public class Customer extends Person {

    @Column(name = "CUSTOMER_ID", unique = true, nullable = false)
    private Long customerId;

    @NotEmpty
    @Size(min=4)
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    private Boolean status;
}
