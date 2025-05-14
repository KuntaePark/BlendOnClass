package com.blendonclass.dto.admin;

import com.blendonclass.entity.Classroom;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ClassroomGenRequest {
    private List<ClassroomDto> classrooms;
}
