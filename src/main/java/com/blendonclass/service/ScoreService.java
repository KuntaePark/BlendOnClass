package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ProgressListDto;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Lesson;
import com.blendonclass.entity.Progress;
import com.blendonclass.entity.Score;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.LessonRepository;
import com.blendonclass.repository.ProgressRepository;
import com.blendonclass.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final ProgressRepository progressRepository;
    private final AuthorityRepository authorityRepository;
    private final LessonRepository lessonRepository;

    public Float getStudentCompleteRate(Long accountId, SUBJECT subject){
        // 학생이 속한 모든 반 조회
        Authority authority = authorityRepository.findByAccountId(accountId);
        if(authority == null) return null;

        Long classroomId = authority.getClassroom().getId();

        //해당 반의 해당 과목 진도 조회
        Progress progress = progressRepository.findByClassroomIdAndStartLesson_Chapter_Subject(classroomId,subject);
        if(progress == null) return null;

        Long startId = progress.getStartLesson().getId();
        Long endId = progress.getEndLesson().getId();

        // 진도 범위 내 Score 조회
        List<Score> scores = scoreRepository.findByAccountIdAndLessonIdBetweenOrderByLessonIdAsc(accountId,startId,endId);
        if(scores.isEmpty()) return 0f;

        // 완료된 소단원 수 계산 - 범위가 소단원3~9까지고 완료한 소단원이 2개라면 2/7 = 0.3 30%
        Long completed = scores.stream().filter(s -> s.getCompleteRate() >= 100).count();

        return (float) completed / scores.size();
    }
}