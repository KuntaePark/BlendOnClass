package com.blendonclass.repository;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByClassroomId(Long classroomId);
    Progress findByClassroomIdAndSubject(Long classroomId, SUBJECT subject);
}
