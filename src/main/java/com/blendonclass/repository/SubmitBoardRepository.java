package com.blendonclass.repository;

import com.blendonclass.entity.SubmitBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitBoardRepository extends JpaRepository<SubmitBoard, Long> {
    public List<SubmitBoard> findByClassroomIdAndAbId(Long classroomId, Long AbId);
    public SubmitBoard findByAccountIdAndAbId(Long accountId, Long abId);
}
