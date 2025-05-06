package com.blendonclass.repository;

import com.blendonclass.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByAccountIdOrderByLessonIdAsc(Long accountId);
    Score findByAccountIdAndLessonId(Long accountId, Long lessonId);
    List<Score> findByAccountIdAndLessonIdBetweenOrderByLessonIdAsc(Long accountId, Long lessonId1, Long lessonId2);
    float findAvgCompleteRateByLessonIdBetween(Long lessonId1, Long lessonId2);
}
