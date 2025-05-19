package com.blendonclass.service;

import com.blendonclass.entity.Alarm;
import com.blendonclass.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    public List<Alarm> getAlarmByClassroomId(Long id){
        List<Alarm> alarms = alarmRepository.findByClassroomId(id);
        return alarms;
    }
}
