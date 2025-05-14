package com.blendonclass.service;

import com.blendonclass.dto.admin.ClassroomDto;
import com.blendonclass.entity.Classroom;
import com.blendonclass.repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public List<ClassroomDto> findAll() {
        List<Classroom> classrooms = classroomRepository.findAllByOrderByGradeAscClassroomNumAsc();
        List<ClassroomDto> classroomDtos = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            classroomDtos.add(ClassroomDto.from(classroom));
        }
        return classroomDtos;
    }
}
