package com.blendonclass.dto;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Classroom;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ClassroomListDto {
    private Long classroomId;
    private int grade;
    private int classroomNum;
    private String subject;

    public static ClassroomListDto fromAuthority(Authority authority) {
        ClassroomListDto dto = new ClassroomListDto();
        Classroom classroom = authority.getClassroom();
        dto.setClassroomId(classroom.getId());
        dto.setGrade(classroom.getGrade());
        dto.setClassroomNum(classroom.getClassroomNum());
        dto.setSubject(authority.getAuthType().getSubject());
        return dto;
    }

}
