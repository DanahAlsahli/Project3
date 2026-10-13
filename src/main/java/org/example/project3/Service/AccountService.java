package org.example.project3.Service;
import lombok.RequiredArgsConstructor;
import org.example.project3.Api.ApiException;
import org.example.project3.Model.Account;
import org.example.project3.Model.Customer;
import org.example.project3.Model.User;
import org.example.project3.Repository.AccountRepository;
import org.example.project3.Repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    private final SecureRandom random = new SecureRandom();


    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }


    public Account getAccountById(User user, Integer accountId) {

        Account account = findAccount(accountId);
        boolean isOwner = account.getCustomer().getUser().getId().equals(user.getId());
        boolean isStaff = user.getRole().equals("ADMIN") || user.getRole().equals("EMPLOYEE");

        if (!isOwner && !isStaff) {
            throw new ApiException("access denied");
        }

        return account;
    }


    public List<Account> getMyAccounts(Integer userId) {

        Customer customer = customerRepository.findCustomerByUserId(userId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }

        return accountRepository.findAccountsByCustomerId(customer.getId());
    }

    @Transactional
    public void createAccount(Integer userId) {

        Customer customer = customerRepository.findCustomerByUserId(userId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(0.0);
        account.setIsActive(false);
        account.setCustomer(customer);

        accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(Integer userId,Integer accountId) {

        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }

        if (!account.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("you do not own this account");
        }

        if (account.getBalance() != 0.0) {
            throw new ApiException("account balance must be zero");
        }

        accountRepository.delete(account);
    }

    @Transactional
    public void activateAccount(Integer accountId) {

        Account account = findAccount(accountId);
        if (account.getIsActive()) {
            throw new ApiException("account is already active");
        }

        account.setIsActive(true);
        accountRepository.save(account);
    }

    @Transactional
    public void deposit(Integer userId,Integer accountId, Double amount) {

        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }

        if (!account.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("you do not own this account");
        }

        if (!account.getIsActive()) {
            throw new ApiException("account is not active");
        }

        if (amount == null || !Double.isFinite(amount) || amount <= 0) {
            throw new ApiException("invalid amount");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(Integer userId,Integer accountId, Double amount) {

        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }

        if (!account.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("you do not own this account");
        }

        if (!account.getIsActive()) {
            throw new ApiException("account is not active");
        }

        if (amount == null || !Double.isFinite(amount) || amount <= 0) {
            throw new ApiException("invalid amount");
        }

        if (account.getBalance() < amount) {
            throw new ApiException("insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(Integer userId,Integer fromAccountId, Integer toAccountId, Double amount) {

        if (fromAccountId.equals(toAccountId)) {
            throw new ApiException("cannot transfer to the same account");
        }

        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);

        if (fromAccount == null || toAccount == null) {
            throw new ApiException("account not found");
        }

        if (!fromAccount.getCustomer().getUser().getId().equals(userId)) {
            throw new ApiException("you do not own this account");
        }

        if (!fromAccount.getIsActive() || !toAccount.getIsActive()) {
            throw new ApiException("account is not active");
        }

        if (amount == null || !Double.isFinite(amount) || amount <= 0) {
            throw new ApiException("invalid amount");
        }

        if (fromAccount.getBalance() < amount) {
            throw new ApiException("insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Transactional
    public void blockAccount(Integer accountId) {

        Account account = findAccount(accountId);
        if (!account.getIsActive()) {
            throw new ApiException("account is already inactive");
        }

        account.setIsActive(false);

        accountRepository.save(account);
    }

    private Account findAccount(Integer accountId) {

        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }

        return account;
    }

    private String generateAccountNumber() {

        for (int i = 0; i < 10; i++) {
            String number = String.format("%04d-%04d-%04d-%04d", random.nextInt(10000), random.nextInt(10000), random.nextInt(10000), random.nextInt(10000));
            if (!accountRepository.existsByAccountNumber(number)) {
                return number;
            }
        }

        throw new ApiException("unable to generate account number");
    }
}
