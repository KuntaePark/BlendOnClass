package com.blendonclass.repository;

import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByAccountId(Long accountId);

    List<Authority> findByClassroom(Classroom classroom);

    Optional<Authority> findByAccountIdAndClassroomId(Long accountId, Long accountId1);

    Authority findByAccount_Id(Long studentId);

    List<Authority> findByClassroom_Id(Long classroomId);
}
