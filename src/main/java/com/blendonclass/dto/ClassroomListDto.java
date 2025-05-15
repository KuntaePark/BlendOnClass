package com.blendonclass.dto;

import com.blendonclass.constant.SUBJECT;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClassroomListDto {
    private Long classroomId;
    private int grade;
    private int classroomNum;
    private SUBJECT authType;
}
