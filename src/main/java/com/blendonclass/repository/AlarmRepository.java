package com.blendonclass.repository;

import com.blendonclass.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    public List<Alarm> findByOrderByAlarmDesc(int alarm);
}
