package com.blendonclass.repository;

import com.blendonclass.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByGrade(int grade);
    List<Lesson> findByChapId(Long chapId);
}
