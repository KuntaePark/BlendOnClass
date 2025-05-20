package com.blendonclass.repository;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonScoreDto;
import com.blendonclass.entity.Account;
import com.blendonclass.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> account(Account account);
    Optional<Score> findByAccountIdAndLessonId(Long accountId, Long lessonId);
    List<Score> findByAccountIdAndLessonIdBetweenOrderByLessonIdAsc(Long accountId, Long lessonId1, Long lessonId2);

    @Query("""
    select new com.blendonclass.dto.LessonScoreDto(
        c.id, c.grade, c.subject, c.title, l.id, l.lessonTitle,
            l.lessonType, s.completeRate, s.attemptCount
        )
            from Chapter c
                         join Lesson l on l.chapter = c
                         left join Score s on s.lesson = l and s.account.id = :accountId
                         where c.grade = :grade and c.subject = :subject
                         order by l.id asc
    """)
    List<LessonScoreDto> findScoresByGradeAndSubjectAndAccountId(int grade, SUBJECT subject, Long accountId);
}
