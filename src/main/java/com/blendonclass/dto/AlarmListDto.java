package com.blendonclass.dto;

import com.blendonclass.entity.Alarm;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlarmListDto {
    private LocalDateTime alarmTime;
    private String alarmUrl;
    private Boolean isSystem;
    private String message;

    public static AlarmListDto from(Alarm alarm) {
        AlarmListDto dto = new AlarmListDto();
        dto.setAlarmTime(alarm.getAlarmTime());
        dto.setAlarmUrl(alarm.getAlarmUrl());
        dto.setIsSystem(alarm.isSystem());
        dto.setMessage(alarm.getMessage());
        return dto;
    }
}
