package com.account_service.services;

import com.account_service.entity.Account;

import java.util.List;

public interface AccountService {

    //create

    Account create(Account account);

    //get accounts

    List<Account> getAccounts();

    //get single account

    Account getAccount(String id);

    //get single account using customerId

    List<Account> getAccountByCustomerId(String customerId);

    //update Account

    Account updateAccount(String id, Account account);

    //update Balance

    Account addBalance(String id, int amount, String customerId);
    Account withdrawBalance(String id, int amount, String customerId);

    //delete

    void delete(String id);

    void deleteAccountUsingCustomerId(String customerId);

}
