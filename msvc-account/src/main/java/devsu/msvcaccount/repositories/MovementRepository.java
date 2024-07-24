package devsu.msvcaccount.repositories;

import devsu.msvcaccount.models.entity.Movement;
import devsu.msvcaccount.repositories.interfaces.IMovementReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long>{
    @Query("SELECT " +
            "m.date as date, " +
            "ac.number as number, " +
            "act.name as type, " +
            "m.balance as balance, " +
            "ac.status as status, " +
            "m.value as value, " +
            "mt.name as movementTypeName " +
            "FROM Movement m " +
            "JOIN MovementType mt ON mt.id = m.movementTypeId " +
            "JOIN Account ac ON ac.id = m.accountId " +
            "JOIN AccountType act ON act.id = ac.accountTypeId " +
            "WHERE ac.customerId = ?1 AND m.date BETWEEN ?2 AND ?3 ")
    List<IMovementReport> getMovementsReport(Long customerId, LocalDate startDate, LocalDate endDate);
}
