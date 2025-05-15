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
    private final ClassroomScoreRepository classroomScoreRepository;
    private final LessonDetailRepository lessonDetailRepository;
    private final LessonRecordRepository lessonRecordRepository;
    private final ChapterRepository chapterRepository;

    public List<ChapterDto> getAllChapters(int grade, SUBJECT subject, Long loggedId) {
        return chapterRepository.findByGradeAndSubject(grade, subject)
                .stream()
                .map(chapter -> {
                    List<LessonDto> lessonDtos = getAllLessonsOfChapter(chapter.getId(), loggedId);
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

    public List<LessonDto> getAllLessonsOfChapter(Long chapId, Long accountId){
        // 해당 대단원의 모든 소단원 조회
        List<Lesson> lessons = lessonRepository.findByChapter_Id(chapId);

        // 각 소단원에 대한 진도율을 조회하여 LessonDto로 변환
        List<LessonDto> lessonDtos = new ArrayList<>();
        for(Lesson lesson : lessons){
            // 각 강의에 대해 ScoreRepository에서 진도율 조회
            Optional<Score> optionalScore = scoreRepository.findByAccountIdAndLessonId(accountId, lesson.getId());
            Score score = optionalScore.orElse(null);
            int completeRate = (score != null) ? score.getCompleteRate() : 0;
            System.out.println(completeRate);
            // LessonDto로 변환하여 리스트에 추가
            LessonDto dto = LessonDto.from(lesson, completeRate);
            lessonDtos.add(dto);

        }
        return lessonDtos;
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


}
















