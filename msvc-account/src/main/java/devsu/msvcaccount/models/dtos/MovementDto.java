package devsu.msvcaccount.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MovementDto {
    private Long AccountNumber;

    private String movementType;

    private BigDecimal value;

    private Boolean status;

}
