package com.blendonclass.repository;

import com.blendonclass.constant.ROLE;
import com.blendonclass.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByMemberId(Long memberId);
    List<Authority> findByClassroomIdAndRole(Long classroomId, ROLE role);

}
