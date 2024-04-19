package com.example.authentication.repository;

import com.example.authentication.entity.Account;
import com.example.authentication.request.account.AccountFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
            select x1 from Account x1
            where x1.isDeleted = false and x1.id = :accountId
            """)
    Optional<Account> findById(Long accountId);

    @Query("""
            select x1 from Account x1
            where x1.isDeleted = false and x1.email = :username
            """)
    Optional<Account> findAccountByEmail(String username);

    @Modifying
    @Query("UPDATE Account a SET a.isActive = false WHERE a.email = :email")
    void updateAccountActive(String email);

    String GET_LIST_ACCOUNT = """
            where x1.isDeleted = false
            and ( :#{#request.keyword} is null or CONCAT(coalesce(x1.name,''),x1.email) like CONCAT('%',:#{#request.keyword},'%'))
            """;

    @Query(value = "select x1 from Account x1 " + GET_LIST_ACCOUNT
            , countQuery = "select count(distinct x1.id) from Account x1 " + GET_LIST_ACCOUNT)
    Page<Account> getListAccount(AccountFilterRequest request, Pageable pageable);
}
