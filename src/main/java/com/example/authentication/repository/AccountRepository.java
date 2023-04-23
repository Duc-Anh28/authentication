package com.example.authentication.repository;

import com.example.authentication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select x1 from Account x1 \n" +
            " where x1.isDeleted = false and x1.email = :username")
    Optional<Account> findByEmail(String username);
}
	