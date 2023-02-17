package com.account_service.services.Impl;

import com.account_service.entity.Account;
import com.account_service.entity.Customer;
import com.account_service.exceptions.ResourceNotFoundException;
import com.account_service.repositories.AccountRepository;
import com.account_service.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account create(Account account) {
        String accountId = UUID.randomUUID().toString();
        account.setAccountId(accountId);


        Date current_Date = new Date();

        account.setAccountOpeningDate(current_Date);
        account.setLastActivity(current_Date);

        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(String id) {

        // Getting Accounts from ACCOUNT SERVICE


            Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

            // Getting customers from USER SERVICE


            Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/" + account.getCustomerId(), Customer.class);


            account.setCustomer(customer);

            return account;



    }

    @Override
    public List<Account> getAccountByCustomerId(String customerId) {
        return accountRepository.findByCustomerId(customerId);
    }


    @Override
    public Account updateAccount(String id, Account account) {

        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found  try again with correct details!!"));
        newAccount.setAccountType(account.getAccountType());
        newAccount.setLastActivity(new Date());
        return accountRepository.save(newAccount);
    }

    @Override
    public Account addBalance(String id, int amount, String customerId) {


            Account newAccount = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

            Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/" + customerId, Customer.class);

            if (customer == null) {

                throw new ResourceNotFoundException("Customer with given id not found try again with correct details !!");
            } else {

                int newBalance = newAccount.getBalance() + amount;
                newAccount.setBalance(newBalance);
                newAccount.setLastActivity(new Date());

                return accountRepository.save(newAccount);
            }


    }

    @Override
    public Account withdrawBalance(String id, int amount, String customerId) {


            Account newAccount = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

            Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/" + customerId, Customer.class);

            if (customer == null) {
                throw new ResourceNotFoundException("Customer with given id not found try again with correct details !!");
            } else {

                int newBalance = newAccount.getBalance() - amount;
                newAccount.setBalance(newBalance);
                newAccount.setLastActivity(new Date());
                return accountRepository.save(newAccount);
            }



    }


    @Override
    public void delete(String id) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found !!"));
        this.accountRepository.delete(account);

    }

    @Override
    public void deleteAccountUsingCustomerId(String customerId) {

        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        for( Account account : accounts)
        {
            this.accountRepository.delete(account);
        }



    }


}
