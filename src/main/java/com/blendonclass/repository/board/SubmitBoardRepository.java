package com.blendonclass.repository.board;

import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.entity.SubmitBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitBoardRepository extends JpaRepository<SubmitBoard, Long> {
    public List<SubmitBoard> findByAssignmentBoard(AssignmentBoard assignmentBoard);

}
