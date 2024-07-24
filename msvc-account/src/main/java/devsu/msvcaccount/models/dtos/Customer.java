package devsu.msvcaccount.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {

    private Long id;

    private String name;

    private String gender;

    private Integer age;

    private String identification;

    private String address;

    private String phone;

    private String clienteId;

    private String password;

    private Boolean status;
}
