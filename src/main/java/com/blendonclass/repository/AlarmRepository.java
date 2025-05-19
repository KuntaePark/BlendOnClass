package com.blendonclass.repository;

import com.blendonclass.dto.AlarmListDto;
import com.blendonclass.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByClassroom_IdOrderByAlarmTimeDesc(Long id);
    List<Alarm> findByAccount_IdOrderByAlarmTimeDesc(Long id);
    List<Alarm> findByAccount_IdAndClassroom_IdOrderByAlarmTimeDesc(Long accountId, Long classroomId);

    List<Alarm> findByAccount_IdAndIsSystemEqualsOrderByAlarmTimeDesc(Long id, boolean b);
}
