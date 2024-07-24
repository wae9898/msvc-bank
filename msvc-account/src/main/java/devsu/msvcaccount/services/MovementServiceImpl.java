package devsu.msvcaccount.services;

import devsu.msvcaccount.clients.CustomerClientRest;
import devsu.msvcaccount.handlers.ResourceNotFoundException;
import devsu.msvcaccount.models.dtos.*;
import devsu.msvcaccount.models.entity.Account;
import devsu.msvcaccount.models.entity.Movement;
import devsu.msvcaccount.models.entity.MovementType;
import devsu.msvcaccount.repositories.AccountRepository;
import devsu.msvcaccount.repositories.MovementRepository;
import devsu.msvcaccount.repositories.MovementTypeRepository;
import devsu.msvcaccount.repositories.interfaces.IMovementReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovementServiceImpl implements MovementService{

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementTypeRepository movementTypeRepository;

    @Autowired
    private CustomerClientRest customerClientRest;

    @Override
    public List<Movement> getMovements() {
        try{
            return movementRepository.findAll();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public Movement createMovement(MovementDto movement) throws Exception {
        Account account = accountRepository.findByNumber(movement.getAccountNumber());
        if (account == null) {
            throw new Exception("The account does not exist within the database.");
        }

        MovementType movementType = movementTypeRepository.findByName(movement.getMovementType());
        Movement movementCreate = new Movement();
        movementCreate.setBalance(account.getInitialBalance());
        movementCreate.setValue(movement.getValue());
        movementCreate.setAccountId(account.getId());
        movementCreate.setMovementTypeId(movementType.getId());

        if (Objects.equals(movementType.getName(), "RETIRO")) {
            account.retiro(movement.getValue());
        } else {
            account.deposito(movement.getValue());
        }

        accountRepository.save(account);
        return movementRepository.save(movementCreate);
    }

    @Override
    public Movement getMovement(Long id) throws Exception {
        return movementRepository.findById(id)
                .orElseThrow(() -> new Exception("Movement not found"));
    }

    @Override
    public Movement updateMovement(Long id, Movement movement) throws Exception {
        Movement movementToUpdate = movementRepository.findById(id)
                .orElseThrow(() -> new Exception("Movement not found"));

        movementToUpdate.setMovementTypeId(movement.getMovementTypeId());
        movementToUpdate.setValue(movement.getValue());
        movementToUpdate.setBalance(movement.getBalance());
        movementToUpdate.setAccountId(movement.getAccountId());

        return movementRepository.save(movementToUpdate);
    }

    @Override
    public void deleteMovement(Long id) throws Exception {
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new Exception("Movement not found"));
        movementRepository.delete(movement);
    }

    @Override
    public List<MovementReportResponse> getMovementsReport(LocalDate startDate, LocalDate endDate, Long customerId) {
        Customer customer = customerClientRest.getCustomerByCustomerId(customerId);

        if(customer == null){
            throw new ResourceNotFoundException("Error getting customer: NOT FOUND", "customerId", customerId.toString());
        }

        List<IMovementReport> movementsReport = movementRepository.getMovementsReport(customerId, startDate, endDate);

        if(movementsReport.isEmpty()){
            throw new ResourceNotFoundException("Error getting movements report: NOT FOUND", "customerId", customerId.toString());
        }

        return movementsReport.stream().map(movementReport -> {
            MovementReportResponse movementReportResponse1 = new MovementReportResponse();
            movementReportResponse1.setDate(movementReport.getDate());
            movementReportResponse1.setCustomerName(customer.getName());
            movementReportResponse1.setCustomerNumber(movementReport.getNumber());
            movementReportResponse1.setAccountType(movementReport.getType());
            movementReportResponse1.setInitialBalance(movementReport.getBalance());
            movementReportResponse1.setStatus(movementReport.getStatus());
            movementReportResponse1.setMovementTypeName(movementReport.getMovementTypeName());
            movementReportResponse1.setStatus(movementReport.getStatus());
            movementReportResponse1.setValueMovement(movementReport.getValue());
            BigDecimal availableBalance = (Objects.equals(movementReport.getMovementTypeName(), "RETIRO"))?
                    movementReport.getBalance().subtract(movementReport.getValue()): movementReport.getBalance().add(movementReport.getValue());
            movementReportResponse1.setAvailableBalance(availableBalance);
            return movementReportResponse1;
        }).collect(Collectors.toList());
    }
}
