package com.blendonclass.repository;

import com.blendonclass.entity.ClassroomScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClassroomScoreRepository extends JpaRepository<ClassroomScore, Long> {
    List<ClassroomScore> findByClassroomIdOrderByLessonIdAsc(Long classroomId);
    List<ClassroomScore> findByClassroomIdAndLessonIdBetweenOrderByLessonIdAsc(Long classroomId, Long lessonId1, Long lessonId2);
    float findAvgCompleteRateByClassroomIdAndLessonIdBetween(Long classroomId, Long lessonId1, Long lessonId2);
}
