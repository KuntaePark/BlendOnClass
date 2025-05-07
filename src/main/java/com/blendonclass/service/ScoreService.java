package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ScoreDataDto;
import com.blendonclass.dto.ScoreUnit;
import com.blendonclass.entity.ClassroomScore;
import com.blendonclass.entity.Score;
import com.blendonclass.repository.*;
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

}
