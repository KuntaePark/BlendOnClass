package com.blendonclass.repository;

import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.entity.QuestionBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {
    public List<QuestionBoard> findAllByClassroomIdOrderByWriteTimeDesc(Long classroomId, Pageable pageable);
}
