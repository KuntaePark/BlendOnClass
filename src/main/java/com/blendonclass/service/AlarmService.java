package com.blendonclass.service;

import com.blendonclass.dto.AlarmListDto;
import com.blendonclass.entity.Alarm;
import com.blendonclass.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public List<AlarmListDto> getAlarmByClassroomId(Long id){
        return alarmRepository.findByClassroom_IdOrderByAlarmTimeDesc(id).stream().map(AlarmListDto::from).collect(Collectors.toList());
    }

    public List<AlarmListDto> getAlarmByAccountId(Long id) {
        return alarmRepository.findByAccount_IdOrderByAlarmTimeDesc(id).stream().map(AlarmListDto::from).collect(Collectors.toList());
    }

    public List<AlarmListDto> getAlarmByAccountIdAndClassroomId(Long accountId, Long classroomId) {
        return alarmRepository.findByAccount_IdAndClassroom_IdOrderByAlarmTimeDesc(accountId, classroomId)
                .stream().map(AlarmListDto::from).collect(Collectors.toList());
    }

    public List<AlarmListDto> getTeacherAlarm(Long accountId, Long classroomId) {
        //시스템 공지, 반 질문 올라옴, 과제 누군가 제출함
        return null;
    }

    public List<AlarmListDto> getStudentAlarm(Long accountId) {
        return null;
    }

    public List<AlarmListDto> getSystemAlarm(Long id) {
        return alarmRepository.findByAccount_IdAndIsSystemEqualsOrderByAlarmTimeDesc(id, true)
                .stream().map(AlarmListDto::from).toList();
    }
}
