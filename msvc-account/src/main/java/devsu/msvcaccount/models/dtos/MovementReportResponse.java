package devsu.msvcaccount.models.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MovementReportResponse {

    private LocalDate date;

    private String customerName;

    private Long customerNumber;

    private String movementTypeName;

    private BigDecimal initialBalance;

    private Boolean status;

    private BigDecimal valueMovement;

    private BigDecimal availableBalance;

    private String accountType;

}
