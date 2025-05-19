package com.blendonclass.repository;

import com.blendonclass.constant.ROLE;
import com.blendonclass.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    //계정 검색용
    Page<Account> findByNameContainingAndRoleNot(String keyword, ROLE role, Pageable pageable);
    Page<Account> findByRoleAndNameContainingAndRoleNot(ROLE role, String keyword, ROLE role2, Pageable pageable);

    Optional<Account> findByLoginId(String loginId);

    @Query("select a.email from Account a")
    List<String> findAllEmails();

    @Query("select a.email from Account a where a.id != :id")
    List<String> findAllEmailsExcept(@Param("id") Long id);

}
