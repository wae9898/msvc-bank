package devsu.msvcaccount.services;

import devsu.msvcaccount.clients.CustomerClientRest;
import devsu.msvcaccount.models.dtos.Customer;
import devsu.msvcaccount.models.entity.Account;
import devsu.msvcaccount.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerClientRest customerClientRest;

    @Override
    public List<Account> getAccounts() {
        try{
            return accountRepository.findAll();
        } catch (Exception e){
            logger.error("Accounts empty list, error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional
    @Override
    public Account createAccount(Account account) throws Exception {
        try{
            if (accountRepository.existsByNumber(account.getNumber())) {
                logger.error("Duplicate key accountId violates unique constraint");
                throw new Exception("Duplicate key, account number violates unique constraint");
            }

            Customer customer = customerClientRest.getCustomerByCustomerId(account.getCustomerId());
            if (customer == null) {
                throw new Exception("The clientId does not exist within the database.");
            }

            return accountRepository.save(account);
        }catch(Exception e ){
            logger.error("An error ocurrred while crating a account: "  + e.getMessage());
            throw new Exception("An error ocurrred while crating a account: "  + e.getMessage());
        }
    }

    @Override
    public Account getAccount(Long id) throws Exception {
        try{
            return accountRepository.findById(id)
                    .orElseThrow(() -> new Exception("Account not found"));
        }catch (Exception e){
            logger.error("An error occurred while get account: " + e.getMessage());
            throw new Exception("An error ocurred whiole get account " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Account updateAccount(Long id, Account account) throws Exception {
        try{
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
        }catch (Exception e){
            logger.warn("An error occurred while update account: " + e.getMessage());
            throw new Exception("An error occurred while update account: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) throws Exception {
        try{
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new Exception("Account not found"));
            accountRepository.delete(account);
        }catch (Exception e ){
            logger.warn("An error occurred while delete account: " + e.getMessage());
            throw new Exception("An error occurred while delete account: " + e.getMessage());
        }
    }
}
