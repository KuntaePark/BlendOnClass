package com.blendonclass.repository;

import com.blendonclass.entity.SubmitBoard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitBoardRepository {
    public List<SubmitBoard> findByClassroomIdAndAbId(Long classroomId, Long AbId);
    public SubmitBoard findByAccountIdAndAbId(Long accountId, Long abId);
}
