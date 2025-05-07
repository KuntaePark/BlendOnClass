package com.blendonclass.repository;

import com.blendonclass.entity.AssignmentBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentBoardRepository {
    public List<AssignmentBoard> findAllByClassroomIdOrderByWriteTimeDesc(Long classroomId, Pageable pageable);
    public AssignmentBoard findById(Long ab_id);
}
