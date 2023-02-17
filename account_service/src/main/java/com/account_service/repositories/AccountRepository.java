package com.account_service.repositories;

import com.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,String> {

    //Find accounts using userId
    List<Account> findByCustomerId(String customerId);

}
