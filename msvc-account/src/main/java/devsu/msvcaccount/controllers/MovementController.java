package devsu.msvcaccount.controllers;

import devsu.msvcaccount.handlers.ValidateHandler;
import devsu.msvcaccount.models.dtos.ApiError;
import devsu.msvcaccount.models.dtos.MovementDto;
import devsu.msvcaccount.models.dtos.MovementReportResponse;
import devsu.msvcaccount.models.entity.Account;
import devsu.msvcaccount.models.entity.Movement;
import devsu.msvcaccount.services.MovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @Autowired
    private ValidateHandler validateHandler;

    @GetMapping()
    public ResponseEntity<?> getMovements() {
        List<Movement> movements = movementService.getMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovementById(@PathVariable Long id) throws Exception {
        Movement movement = movementService.getMovement(id);
        return ResponseEntity.ok(movement);
    }

    @PostMapping()
    public ResponseEntity<?> createMovement(@Valid @RequestBody MovementDto movement, BindingResult result) {
        if (result.hasErrors()) {
            return validateHandler.validate(result);
        }

        try {
            Movement movement1 = movementService.createMovement(movement);
            return ResponseEntity.status(HttpStatus.CREATED).body(movement1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovement(
            @Valid @RequestBody Movement movement,@PathVariable Long id, BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            return validateHandler.validate(result);
        }
        Movement movement1 = movementService.updateMovement(id, movement);
        return ResponseEntity.ok(movement1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovement(@PathVariable Long id) throws Exception {
        try {
            movementService.deleteMovement(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred")
            );
        }
    }

    @GetMapping("/report")
    public List<MovementReportResponse> getMovementsReport(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam Long customerId
    ) {
        return movementService.getMovementsReport(startDate, endDate, customerId);
    }
}