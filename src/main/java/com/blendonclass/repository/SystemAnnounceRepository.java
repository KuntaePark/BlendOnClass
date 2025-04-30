package com.blendonclass.repository;

import com.blendonclass.entity.SystemAnnounce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemAnnounceRepository extends JpaRepository<SystemAnnounce, Integer> {

}
