package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ProgressListDto;
import com.blendonclass.entity.Progress;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.ClassroomScoreRepository;
import com.blendonclass.repository.ProgressRepository;
import com.blendonclass.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final ClassroomScoreRepository classroomScoreRepository;
    private final ProgressRepository progressRepository;
    private final AuthorityRepository authorityRepository;

    public ProgressListDto getClassroomCompleteRate(Long classroomId, SUBJECT subject) {
        //해당 반에 대한 진도 목록 전체
        List<Progress> progressList = progressRepository.findByClassroomId(classroomId);
        Progress curProgress = null;
        for(Progress progress : progressList) {
            if(subject == progress.getStartLesson().getChapter().getSubject()) {
                curProgress = progress;
                break;
            }
        }
        //해당 과목에 대한 진도 없음. 그냥 나감
        if(curProgress == null) return null;

        //진도를 찾았으니 해당 진도에 대해 완수율 계산


        return null;
    }
}
