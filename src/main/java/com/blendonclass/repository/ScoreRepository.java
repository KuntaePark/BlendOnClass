package com.blendonclass.repository;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonScoreDto;
import com.blendonclass.entity.Account;
import com.blendonclass.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.security.auth.Subject;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> account(Account account);

    Score findByLessonIdAndAccountId(Long id, Long accountId);
    List<Score> findByAccountIdOrderByLessonIdAsc(Long accountId);
    Optional<Score> findByAccountIdAndLessonId(Long accountId, Long lessonId);
    List<Score> findByAccountIdAndLessonIdBetweenOrderByLessonIdAsc(Long accountId, Long lessonId1, Long lessonId2);
    float findAvgCompleteRateByLessonIdBetween(Long lessonId1, Long lessonId2);

    @Query("""
    select new com.blendonclass.dto.LessonScoreDto(
        c.id, c.grade, c.subject, c.title, l.id, l.lessonTitle,
            l.lessonType, s.completeRate, s.attemptCount
        )
    from Score s
    join s.lesson l
    join l.chapter c
    where c.grade = :grade and c.subject = :subject and s.account.id = :accountId
    order by l.id asc
    """)
    List<LessonScoreDto> findScoresByGradeAndSubjectAndAccountId(int grade, SUBJECT subject, Long accountId);
}
