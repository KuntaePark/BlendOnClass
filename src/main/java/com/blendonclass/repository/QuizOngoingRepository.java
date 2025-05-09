package com.blendonclass.repository;

import com.blendonclass.entity.QuizOngoing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizOngoingRepository extends JpaRepository<QuizOngoing, Long> {
    Optional<QuizOngoing> findByAccountIdAndLessonId(Long accountId, Long lessonId);


}