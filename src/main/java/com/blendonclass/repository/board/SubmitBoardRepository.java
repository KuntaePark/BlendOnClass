package com.blendonclass.repository.board;

import com.blendonclass.dto.SubmitStudentListDto;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.entity.SubmitBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitBoardRepository extends JpaRepository<SubmitBoard, Long> {
    public List<SubmitBoard> findByAssignmentBoard(AssignmentBoard assignmentBoard);

    //해당 반의 해당 과제 제출물 전부 검색
    @Query("""
        select new com.blendonclass.dto.SubmitStudentListDto (
                a.id, ab.id, a.name, a.email, true
                )
                from SubmitBoard sb join sb.assignmentBoard ab join ab.authority auth join auth.classroom c join auth.account a
                where ab.id = :abId and c.id = :classroomId
        """)
    List<SubmitStudentListDto> findSubmitBoardByAbIdAndClassroomId(Long abId, Long classroomId);

}
