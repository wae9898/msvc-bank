package devsu.msvcaccount.controllers;

import devsu.msvcaccount.handlers.ValidateHandler;
import devsu.msvcaccount.models.dtos.ApiError;
import devsu.msvcaccount.models.entity.Account;
import devsu.msvcaccount.services.AccountService;
import devsu.msvcaccount.services.AccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ValidateHandler validateHandler;

    @GetMapping()
    public ResponseEntity<?> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) throws Exception {
        Account account = accountService.getAccount(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping()
    public ResponseEntity<?> createAccount(@Valid @RequestBody Account account, BindingResult result) {
        if (result.hasErrors()) {
            return validateHandler.validate(result);
        }
        try {
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody Account account,@PathVariable Long id, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return validateHandler.validate(result);
        }
        Account account1 = accountService.updateAccount(id, account);
        return ResponseEntity.ok(account1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred")
            );
        }
    }

}
