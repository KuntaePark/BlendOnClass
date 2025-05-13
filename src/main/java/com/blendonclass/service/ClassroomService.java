package com.blendonclass.service;

import com.blendonclass.entity.Classroom;
import com.blendonclass.repository.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;


    public ClassroomService(ClassroomRepository classroomRepository){
        this.classroomRepository = classroomRepository;
    }

    // 학년 전체 반 목록 조회
    public List<Classroom> getClassroomByGrade(){
        return classroomRepository.findAll();
    }

    // 특정 학년 + 반 번호로 반 정보 조회
    public Classroom getClassroomByGradeAndNum(int grade, int classroomNum){
        return classroomRepository.findByGradeAndClassroomNum(grade, classroomNum);
    }
}
