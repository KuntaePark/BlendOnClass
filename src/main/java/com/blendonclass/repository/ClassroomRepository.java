package com.blendonclass.repository;

import com.blendonclass.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Classroom findByGradeAndClassroomNum(int grade, int classroomNum);

    @Query("select c.grade from Classroom c where c.id = :classroomId")
    int findGradeById(Long classroomId);

    List<Classroom> findAllByOrderByGradeAscClassroomNumAsc();
}
