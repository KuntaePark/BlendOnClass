package com.blendonclass.repository;

import com.blendonclass.entity.ClassroomScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomScoreRepository extends JpaRepository<ClassroomScore, Long> {

    ClassroomScore findByLessonIdAndClassroomId(Long lessonId, Long classroomId);
}
