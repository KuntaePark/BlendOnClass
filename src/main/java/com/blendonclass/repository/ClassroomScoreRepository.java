package com.blendonclass.repository;

import com.blendonclass.entity.ClassroomScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomScoreRepository extends JpaRepository<ClassroomScore, Long> {
    List<ClassroomScore> findByClassroomIdOrderByLessonAsc(Long classroomId, int grade);
    List<ClassroomScore> findByClassroomIdLessonIdBetweenOrderByLessonLdAsc(Long classroomId, Long lessonId1, Long lessonId2);
}
