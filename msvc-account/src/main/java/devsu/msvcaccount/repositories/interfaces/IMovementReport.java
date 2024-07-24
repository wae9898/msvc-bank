package devsu.msvcaccount.repositories.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IMovementReport {
    LocalDate getDate();

    Long getNumber();

    String getType();

    BigDecimal getBalance();

    Boolean getStatus();

    BigDecimal getValue();

    String getMovementTypeName();
}
