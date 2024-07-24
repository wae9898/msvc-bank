package devsu.msvcaccount.services;

import devsu.msvcaccount.clients.CustomerClientRest;
import devsu.msvcaccount.models.dtos.ApiError;
import devsu.msvcaccount.models.dtos.Customer;
import devsu.msvcaccount.models.entity.Account;
import devsu.msvcaccount.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerClientRest customerClientRest;

    @Override
    public List<Account> getAccounts() {
        try{
            return accountRepository.findAll();
        } catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public Account createAccount(Account account) throws Exception {
        if (accountRepository.existsByNumber(account.getNumber())) {
            throw new Exception("Duplicate key, account number violates unique constraint");
        }

        Customer customer = customerClientRest.getCustomerByCustomerId(account.getCustomerId());
        if (customer == null) {
            throw new Exception("The clientId does not exist within the database.");
        }

        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Long id) throws Exception {
        return accountRepository.findById(id)
                .orElseThrow(() -> new Exception("Account not found"));
    }

    @Override
    public Account updateAccount(Long id, Account account) throws Exception {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new Exception("Account not found"));

        Customer customer = customerClientRest.getCustomerByCustomerId(account.getCustomerId());
        if (customer == null) {
            throw new Exception("The clientId does not exist within the database.");
        }

        existingAccount.setNumber(account.getNumber());
        existingAccount.setCustomerId(account.getCustomerId());
        existingAccount.setAccountTypeId(account.getAccountTypeId());
        existingAccount.setInitialBalance(account.getInitialBalance());
        existingAccount.setStatus(account.getStatus());

        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Long id) throws Exception {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new Exception("Account not found"));
        accountRepository.delete(account);
    }
}
