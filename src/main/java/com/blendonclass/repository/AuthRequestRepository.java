package com.blendonclass.repository;

import com.blendonclass.entity.AuthRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRequestRepository extends JpaRepository<AuthRequest, Long> {
    List<AuthRequest> findAllByOrderByReqTimeDesc(Pageable pageable);
}
