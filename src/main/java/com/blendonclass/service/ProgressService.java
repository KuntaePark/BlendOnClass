package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ProgRateCalcDto;
import com.blendonclass.dto.ProgressDto;
import com.blendonclass.dto.ProgressListDto;
import com.blendonclass.entity.Progress;
import com.blendonclass.repository.ClassroomRepository;
import com.blendonclass.repository.ClassroomScoreRepository;
import com.blendonclass.repository.LessonRepository;
import com.blendonclass.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgressService {
    private final ProgressRepository progressRepository;
    private final ClassroomScoreRepository classroomScoreRepository;
    private final LessonRepository lessonRepository;
    private final ClassroomRepository classroomRepository;

    //해당 반의 해당 과목의 진도 조회
    public Progress getProgress(Long classroomId, SUBJECT subject) {
        return progressRepository.findByClassroomIdAndStartLesson_Chapter_Subject(classroomId, subject);
    }

    public List<ProgressListDto> getProgressesOfClassroom(Long classroomId) {
        List<Progress> progresses = progressRepository.findByClassroomId(classroomId);
        if(progresses != null) {
            //각 진도율에 대해 완료율 조회
            return progresses.stream().map(progress -> {
                List<Float> completeRates =
                        classroomScoreRepository.findCompleteRatesOfProgress(classroomId,
                                progress.getStartLesson().getId(), progress.getEndLesson().getId());
                float sum = completeRates.stream().reduce(0f, (a,b)->a+(b==null?0:b));
                return ProgressListDto.from(progress, sum / completeRates.size());
            }).collect(Collectors.toList());
        } else {
            return null;
        }
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
