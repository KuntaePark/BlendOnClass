package com.blendonclass.repository;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonScoreDto;
import com.blendonclass.entity.ClassroomScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomScoreRepository extends JpaRepository<ClassroomScore, Long> {

    ClassroomScore findByLessonIdAndClassroomId(Long lessonId, Long classroomId);


//    List<LessonScoreDto> findScoresByGradeAndSubjectAndClassroomId(int grade, SUBJECT subject, Long classroomId);
}
