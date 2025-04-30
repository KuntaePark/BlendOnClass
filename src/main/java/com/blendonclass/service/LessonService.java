package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ChapterDto;
import com.blendonclass.dto.LessonDetailDto;
import com.blendonclass.dto.LessonDto;
import com.blendonclass.dto.ProgressListDto;
import com.blendonclass.entity.Chapter;
import com.blendonclass.entity.Lesson;
import com.blendonclass.entity.Progress;
import com.blendonclass.entity.Score;
import com.blendonclass.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ScoreRepository scoreRepository;
    private final LessonDetailRepository lessonDetailRepository;
    private final LessonRecordRepository lessonRecordRepository;
    private final ChapterRepository chapterRepository;

    public List<ChapterDto> getAllChapters(int grade, SUBJECT subject) {
        return chapterRepository.findByGradeAndSubject(grade, subject)
                .stream()
                .map(chapter -> {
                    List<LessonDto> lessonDtos = lessonRepository.findByChapter(chapter)
                            .stream()
                            .map(LessonDto::from).collect(Collectors.toList());
                    return ChapterDto.from(chapter, lessonDtos);
                })
                .collect(Collectors.toList());
    }

    public ProgressListDto getStudentCompleteRate(Long accountId, SUBJECT subject) {
        return scoreRepository.findByAccountIdAndLessonId(Long accountId,
    }
















    public List<LessonDto> getAllLessonsOfChapter(Long chapId, Long accountId){
        return lessonRepository.findByChapId(chapId)
                .stream()
                .map(lesson -> {
                    Score score = scoreRepository.findByAccountIdAndLessonId(accountId, lesson.getId());
                    int completeRate = (score != null) ? score.getCompleteRate() : 0;
                    LessonDto lessonDto = LessonDto.from(lesson);
                    lessonDto.setCompleteRate(completeRate);
                    return LessonDto.from(lesson);
                })
                .collect(Collectors.toList());
    }

    public LessonDetailDto getLessonDetail(Long lessonId, Long accountId) {}

}