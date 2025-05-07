package com.blendonclass.repository;

import com.blendonclass.entity.Account;
import com.blendonclass.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> account(Account account);

    Score findByLessonIdAndAccountId(Long id, Long accountId);
}
