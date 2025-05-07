package com.blendonclass.repository;

import com.blendonclass.entity.Alarm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmBoardRepository {
    public List<Alarm> findByOderByAlarmDesc(int alarm);
}
