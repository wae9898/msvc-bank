package devsu.msvcaccount.services;

import devsu.msvcaccount.models.entity.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {

    List<Account> getAccounts();

    Account createAccount(Account account) throws Exception;

    Account getAccount(Long id) throws Exception;

    Account updateAccount(Long id, Account account) throws Exception;

    void deleteAccount(Long id) throws Exception;
}
