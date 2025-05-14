package com.blendonclass.dto.admin;

import com.blendonclass.entity.Classroom;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ClassroomDto {
    private Long id;
    private int grade;
    private int classroomNum;

    public static ClassroomDto from(Classroom classroom) {
        ClassroomDto dto = new ClassroomDto();
        dto.setId(classroom.getId());
        dto.setGrade(classroom.getGrade());
        dto.setClassroomNum(classroom.getClassroomNum());
        return dto;
    }
}
