package com.blendonclass.repository;

import com.blendonclass.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByLoginId(String loginId);
}
