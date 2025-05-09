package com.blendonclass.repository;

import com.blendonclass.entity.Account;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAccountId(Long accountId);

    List<Authority> findByClassroom(Classroom classroom);
}
