package com.blendonclass.service;

import com.blendonclass.dto.admin.ClassroomDto;
import com.blendonclass.entity.Classroom;
import com.blendonclass.repository.ClassroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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

    public void addClassroom(List<ClassroomDto> classroomDtos) {
        List<Classroom> classrooms = classroomRepository.findAll();
        classroomDtos.forEach(classroomDto -> {
            Classroom newClassroom = classroomDto.to();
            for(Classroom classroom : classrooms) {
                if(classroom.getGrade() == newClassroom.getGrade() && classroom.getClassroomNum() == newClassroom.getClassroomNum()) {
                    throw new IllegalArgumentException("이미 존재하는 반이 있습니다.");
                }
            }
            classroomRepository.save(newClassroom);
        });
    }
}
