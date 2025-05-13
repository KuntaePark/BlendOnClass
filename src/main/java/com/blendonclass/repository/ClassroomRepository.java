package com.blendonclass.repository;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonScoreDto;
import com.blendonclass.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
   Classroom findByGradeAndClassroomNum(int grade, int classroomNum);
   List<Classroom> findByGrade(int grade);

    @Query("select c.grade from Classroom c where c.id = :classroomId")
    int findGradeById(Long classroomId);
}
