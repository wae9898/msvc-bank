package devsu.msvcaccount.models.entity;

import devsu.msvcaccount.handlers.DineroInsuficienteException;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ACCOUNTS")
public class Account {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ACCOUNT_ID", unique = true, nullable = false)
        private Long id;

        @NotNull
        private Long number;

        @NotNull
        @Column(name="CUSTOMER_ID", unique = true)
        private Long customerId;

        @Column(name = "ACCOUNT_TYPE_ID")
        private Long accountTypeId;

        @NotNull
        @Column(name = "INITIAL_BALANCE")
        private BigDecimal initialBalance;

        @AssertTrue
        private Boolean status;

        public void retiro(BigDecimal monto) {
                BigDecimal nuevoSaldo = this.initialBalance.subtract(monto);
                if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
                        throw new DineroInsuficienteException("Saldo no disponible.");
                }
                this.initialBalance = nuevoSaldo;
        }

        public void deposito(BigDecimal monto) {
                this.initialBalance = initialBalance.add(monto);
        }
}
