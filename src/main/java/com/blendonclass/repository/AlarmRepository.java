package com.blendonclass.repository;

import com.blendonclass.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByClassroom_IdOrderByAlarmTimeDesc(Long id);
    List<Alarm> findByAccount_IdOrderByAlarmTimeDesc(Long id);
}
