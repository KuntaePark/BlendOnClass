package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ChapterDto;
import com.blendonclass.dto.LessonDto;
import com.blendonclass.entity.Chapter;
import com.blendonclass.entity.Lesson;
import com.blendonclass.entity.Score;
import com.blendonclass.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

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

    public List<ChapterDto> getAllChapters(int grade, SUBJECT subject){
        List<Chapter> chapters = chapterRepository.findByGradeAndSubject(grade, subject);

        return chapters.stream()
                .map(chapter -> {
                    List<Lesson> lessons = lessonRepository.findByChapId(chapter.getId());
                    List<LessonDto> lessonDtos = lessons.stream()
                            .map(lesson -> LessonDto.from(lesson))
                            .collect(Collectors.toList());
                    return ChapterDto.from(chapter, lessonDtos);
                })
                .collect(Collectors.toList());
    }

}
