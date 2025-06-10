package com.blendonclass.service;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.*;
import com.blendonclass.entity.*;
import com.blendonclass.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ScoreRepository scoreRepository;
    private final LessonDetailRepository lessonDetailRepository;
    private final LessonRecordRepository lessonRecordRepository;
    private final ChapterRepository chapterRepository;

    public List<ChapterDto> getAllChapters(int grade, SUBJECT subject, Long loggedId) {
        return chapterRepository.findByGradeAndSubject(grade, subject)
                .stream()
                .map(chapter -> {
                    List<LessonDto> lessonDtos = scoreRepository.findScoresOfLessonInChapter(chapter.getId(), loggedId);
                    return ChapterDto.from(chapter, lessonDtos);
                })
                .collect(Collectors.toList());
    }


    public LessonDto getLastLesson(Long accountId) {
        // 가장 최근에 수강한 강의 기록을 lr_id 기준으로 가져오기
        LessonRecord lastRecord = lessonRecordRepository.findTopByAccountIdOrderByIdDesc(accountId);

        // 마지막 학습한 강의가 없다면, 첫 번째 강의를 기본값으로 설정
        Lesson lastLesson = (lastRecord != null)
                ? lastRecord.getLesson()
                : lessonRepository.findFirstByOrderByIdAsc();

        // 사용자의 강의에 대한 진도율 조회
        Optional<Score> optionalScore = scoreRepository.findByAccountIdAndLessonId(accountId, lastLesson.getId());
        int completeRate = optionalScore.map(Score::getCompleteRate).orElse(0);


        return LessonDto.from(lastLesson, completeRate);
    }

    public LessonDetailDto getLessonDetail(Long lessonId){
        //현재 강의 정보 가져오기
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow( () -> new EntityNotFoundException("강의가 존재하지 않습니다."));
        // 강의 상세 정보 가져오기
        LessonDetail detail = lessonDetailRepository.findByLessonId(lessonId);
        if(detail == null){
            throw new EntityNotFoundException("강의 상세정보가 존재하지 않습니다.");
        }
        return LessonDetailDto.from(lesson,detail);
    }

    // 강의 이동 메서드
    public Long getPrevLessonId(Long currentId) {
        return lessonRepository.findTopByIdLessThanOrderByIdDesc(currentId)
                .map(Lesson::getId)
                .orElse(null);
    }

    public Long getNextLessonId(Long currentId) {
        return lessonRepository.findTopByIdGreaterThanOrderByIdAsc(currentId)
                .map(Lesson::getId)
                .orElse(null);
    }

    public List<ChapterLessonDto> getAllLessons(int grade, SUBJECT subject) {
        return lessonRepository.findLessonsByGradeAndSubject(grade, subject);
    }

    public LessonDetailDto getLessonDetailSafe(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("강의가 존재하지 않습니다."));
        LessonDetail detail = lessonDetailRepository.findByLessonId(lessonId);

        if(detail == null){
            detail = new LessonDetail();
            detail.setId(lessonId);
            detail.setContext("상세 설명 없음");
            detail.setMediaUrl(null);
        }
        return LessonDetailDto.from(lesson,detail);

    }

    public List<ChapterDto> getChapterWithLessons(int grade, SUBJECT subject) {
        // 1. 대단원 목록 조회
        List<Chapter> chapters = chapterRepository.findByGradeAndSubject(grade, subject);

        // 2. 각 대단원에 대해 소단원 조회 및 DTO 구성
        return chapters.stream()
                .map(chapter -> {
                    // 해당 대단원의 소단원들
                    List<Lesson> lessons = lessonRepository.findByChapter_Id(chapter.getId());

                    // Lesson → LessonDto로 변환
                    List<LessonDto> lessonDtoList = lessons.stream()
                            .map(LessonDto::from)
                            .collect(Collectors.toList());

                    // Chapter → ChapterDto로 변환
                    return ChapterDto.from(chapter, lessonDtoList);
                })
                .collect(Collectors.toList());
    }

}
















