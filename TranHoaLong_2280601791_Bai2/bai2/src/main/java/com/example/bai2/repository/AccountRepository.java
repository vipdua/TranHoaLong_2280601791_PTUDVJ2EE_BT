package com.example.bai2.repository;

import com.example.bai2.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    // Custom query to find Account by login_name
    @Query("SELECT a FROM Account a WHERE a.loginName = :loginName")
    Optional<Account> findByLoginName(String loginName);
}