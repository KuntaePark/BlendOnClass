package com.blendonclass.repository;

import com.blendonclass.dto.LessonDetailDto;
import com.blendonclass.entity.LessonDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonDetailRepository extends JpaRepository<LessonDetail, Long> {
    List<LessonDetailDto> findByLessonId(Long lessonId);
}
