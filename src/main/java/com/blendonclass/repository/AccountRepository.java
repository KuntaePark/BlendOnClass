package com.blendonclass.repository;

import com.blendonclass.constant.ROLE;
import com.blendonclass.entity.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    //계정 검색용
    List<Account> findByNameContaining(String keyword, Pageable pageable);
    List<Account> findByRoleAndNameContaining(ROLE role, String name, Pageable pageable);

    Optional<Account> findByLoginId(String loginId);

    @Query("select a.email from Account a")
    List<String> findAllEmails();
}
