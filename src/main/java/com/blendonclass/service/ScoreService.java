package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonScoreDto;
import com.blendonclass.dto.ScoreDataDto;
import com.blendonclass.dto.ScoreUnit;
import com.blendonclass.entity.ClassroomScore;
import com.blendonclass.dto.ProgressListDto;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Lesson;
import com.blendonclass.entity.Progress;
import com.blendonclass.entity.Progress;
import com.blendonclass.entity.Score;
import com.blendonclass.repository.*;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.ClassroomScoreRepository;
import com.blendonclass.repository.ProgressRepository;
import com.blendonclass.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScoreService {
    private final ChapterRepository chapterRepository;
    private final LessonRepository lessonRepository;
    private final ScoreRepository scoreRepository;
    private final ClassroomScoreRepository classroomScoreRepository;
    private final ProgressRepository progressRepository;
    private final AuthorityRepository authorityRepository;

    public Float getStudentCompleteRate(Long accountId, SUBJECT subject){
        // 학생이 속한 모든 반 조회
        List<Authority> authorities = authorityRepository.findByAccountId(accountId);
        if (authorities == null || authorities.isEmpty()) return null;

// 원하는 subject만 추출 (예: SUBJECT.HR)
        Authority authority = authorities.stream()
                .filter(a -> a.getAuthType() == subject)
                .findFirst()
                .orElse(null);

        if (authority == null) return null;

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

    //todo - 좀 느리다; 리펙토링
    public List<ScoreDataDto> getScoreData(Long id, int grade, SUBJECT subject, boolean isStudent) {
        return chapterRepository.findByGradeAndSubject(grade, subject)
                .stream()
                .map(chapter -> {
                    List<ScoreUnit> scoreUnits = getAllScoreOfChapter(chapter.getId(), id, isStudent);
                    return ScoreDataDto.of(chapter, scoreUnits);
                })
                .collect(Collectors.toList());
    }
    public ProgressListDto getClassroomCompleteRate(Long classroomId, SUBJECT subject) {
        //해당 반에 대한 진도 목록 전체
        List<Progress> progressList = progressRepository.findByClassroomId(classroomId);
        Progress curProgress = null;
        for (Progress progress : progressList) {
            if (subject == progress.getStartLesson().getChapter().getSubject()) {
                curProgress = progress;
                break;
            }
        }
        //해당 과목에 대한 진도 없음. 그냥 나감
        if (curProgress == null) return null;
        return null;

    }

    public List<ScoreUnit> getAllScoreOfChapter(Long chapId, Long id, boolean isStudent) {
        return lessonRepository.findByChapter_Id(chapId).stream()
                .map(lesson -> {
                    float completeRate = 0;
                    float attemptCount = 0;
                    if(isStudent) {
                        Score score = scoreRepository.findByLessonIdAndAccountId(lesson.getId(),id);
                        if(score != null) {
                            completeRate = score.getCompleteRate();
                            attemptCount = score.getAttemptCount();
                        }
                    } else {
                        ClassroomScore classroomScore = classroomScoreRepository.findByLessonIdAndClassroomId(lesson.getId(), id);
                        if(classroomScore != null) {
                            completeRate = classroomScore.getCompleteRate();
                            attemptCount = classroomScore.getAttemptCount();
                        }
                    }
                    ScoreUnit scoreUnit = new ScoreUnit();
                    scoreUnit.setLessonTitle(lesson.getLessonTitle());
                    scoreUnit.setCompleteRate(completeRate);
                    scoreUnit.setAttemptCount(attemptCount);
                    return scoreUnit;
                })
                .collect(Collectors.toList());
    }

    public List<?> getAllLessonScores(int grade, SUBJECT subject, Long id, boolean isStudent) {
        if(isStudent) {
            return scoreRepository.findScoresByGradeAndSubjectAndAccountId(grade, subject, id);
        } else {
            return classroomScoreRepository.findScoresByGradeAndSubjectAndAccountId(grade, subject, id);
        }
    }
}
