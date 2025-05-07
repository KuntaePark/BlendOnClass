package com.blendonclass.repository;

import com.blendonclass.entity.SystemAnnounce;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemAnnounceRepository extends JpaRepository<SystemAnnounce, Integer> {
    List<SystemAnnounce> findAllByOrderByWriteTimeDesc(Pageable pageable);
}
