package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ProgressDto;
import com.blendonclass.entity.Lesson;
import com.blendonclass.entity.Progress;
import com.blendonclass.repository.ClassroomRepository;
import com.blendonclass.repository.LessonRepository;
import com.blendonclass.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgressService {
    private final ProgressRepository progressRepository;
    private final LessonRepository lessonRepository;
    private final ClassroomRepository classroomRepository;

    //해당 반의 해당 과목의 진도 조회
    public Progress getProgress(Long classroomId, SUBJECT subject) {
        return progressRepository.findByClassroomIdAndStartLesson_Chapter_Subject(classroomId, subject);
    }

    public void setProgress(ProgressDto progressDto) {
        Progress progress = progressDto.getId() == null ? new Progress() :
                progressRepository.findById(progressDto.getId()).get();
        progress.setClassroom(classroomRepository.findById(progressDto.getClassroomId()).get());
        progress.setStartLesson(lessonRepository.findById(progressDto.getStartLessonId()).get());
        progress.setEndLesson(lessonRepository.findById(progressDto.getEndLessonId()).get());
        progress.setEndDate(progressDto.getEndDate());

        progressRepository.save(progress);

    }
}
