package com.blendonclass.repository;

import com.blendonclass.entity.LessonDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonDetailRepository extends JpaRepository<LessonDetail, Long> {
    LessonDetail findByLessonId(Long lessonId);
}
