package com.account_service.controller;

import com.account_service.entity.Account;
import com.account_service.payload.ApiResponse;
import com.account_service.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    //Create Account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(account));
    }

    // Get single Account Details
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(accountId));
    }

    // Get Accounts using Customer ID

    @GetMapping("/user/{customerId}")
    public ResponseEntity<List<Account>> getAccountsUsingCustomerID(@PathVariable String customerId)
    {
        return ResponseEntity.ok(accountService.getAccountByCustomerId(customerId));
    }

    // Get all Account Details

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts()
    {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    // update account

    @PutMapping("/{accountID}")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable String accountID){

        return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(accountID,account));
    }

    // Add Money
    @PutMapping("/addmoney/{accountID}")
    public ResponseEntity<Account> addMoney(@PathVariable String accountID,@RequestParam int amount,  @RequestParam String customerId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.addBalance(accountID,amount, customerId));
    }


    // withdraw Money
    @PutMapping("/withdraw/{accountID}")
    public ResponseEntity<Account> withdraw(@PathVariable String accountID,@RequestParam int amount, @RequestParam String customerId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.withdrawBalance(accountID,amount, customerId));
    }

    // Delete Account

    @DeleteMapping("/{accountId}")
    public ApiResponse deleteAccount(@PathVariable String accountId)
    {
        this.accountService.delete(accountId);
        return new ApiResponse("Account is Successfully Deleted", true);
    }

    // Delete Account using customerId

    @DeleteMapping("user/{customerId}")
    public ApiResponse deleteAccountByUserId(@PathVariable String customerId)
    {
        this.accountService.deleteAccountUsingCustomerId(customerId);
        return new ApiResponse(" Accounts with given userId is deleted Successfully", true);

    }

}
