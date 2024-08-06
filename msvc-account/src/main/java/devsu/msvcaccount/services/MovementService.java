package devsu.msvcaccount.services;

import devsu.msvcaccount.models.dtos.MovementDto;
import devsu.msvcaccount.models.dtos.MovementReportResponse;
import devsu.msvcaccount.models.entity.Movement;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface MovementService {

    List<Movement> getMovements();

    Movement createMovement(MovementDto movement) throws Exception;

    Movement  getMovement(Long id) throws Exception;

    Movement  updateMovement(Long id, Movement movement) throws Exception;

    void deleteMovement(Long id) throws Exception;

    List<MovementReportResponse>  getMovementsReport(LocalDate startDate, LocalDate endDate, Long customerId) throws Exception;
}
