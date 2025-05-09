package com.blendonclass.repository;

import com.blendonclass.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Classroom findByGradeAndClassroomNum(int grade, int classroomNum);
    Classroom findByGrade(int grade);

}
