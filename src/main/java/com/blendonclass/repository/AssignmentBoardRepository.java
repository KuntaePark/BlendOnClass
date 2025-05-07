package com.blendonclass.repository;

import com.blendonclass.entity.AssignmentBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentBoardRepository extends JpaRepository<AssignmentBoard, Long> {
    public List<AssignmentBoard> findAllByClassroomIdOrderByWriteTimeDesc(Long classroomId, Pageable pageable);
}
