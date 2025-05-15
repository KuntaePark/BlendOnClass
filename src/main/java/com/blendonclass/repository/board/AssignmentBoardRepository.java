package com.blendonclass.repository.board;

import com.blendonclass.entity.AssignmentBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentBoardRepository extends JpaRepository<AssignmentBoard, Long> {
    @Query("""
        select ab
        from AssignmentBoard ab
        join Authority a on ab.authority = a
        where a.classroom.id = :classroomId
        order by ab.writeTime desc
    """)
    List<AssignmentBoard> findAssignmentBoardByClassroomId(@Param("classroomId") Long classroomId, Pageable pageable);
}
