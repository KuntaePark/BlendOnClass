package com.blendonclass.dto.admin;

/*
    권한 요청 목록 표시용
 */

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.entity.AuthRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class AuthReqListDto {
    private Long id;
    private String name;
    private String email;
    private int grade;
    private int classroomNum;
    private SUBJECT authType;
    private LocalDateTime reqTime;

    public static AuthReqListDto from(AuthRequest authRequest) {
        AuthReqListDto authReqListDto = new AuthReqListDto();
        authReqListDto.setId(authRequest.getId());
        authReqListDto.setName(authRequest.getAccount().getName());
        authReqListDto.setEmail(authRequest.getAccount().getEmail());
        authReqListDto.setGrade(authRequest.getClassroom().getGrade());
        authReqListDto.setClassroomNum(authRequest.getClassroom().getClassroomNum());
        authReqListDto.setAuthType(authRequest.getAuthType());
        authReqListDto.setReqTime(authRequest.getReqTime());

        return authReqListDto;
    }
}
