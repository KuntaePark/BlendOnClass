package com.blendonclass.service;

import com.blendonclass.dto.QuizAnsweredDto;
import com.blendonclass.dto.QuizDetailDto;
import com.blendonclass.dto.QuizGradedDto;
import com.blendonclass.entity.Lesson;
import com.blendonclass.entity.Quiz;
import com.blendonclass.entity.QuizOngoing;
import com.blendonclass.entity.Score;
import com.blendonclass.repository.*;
import com.blendonclass.repository.QuizOngoingRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final LessonRepository lessonRepository;
    private final QuizOngoingRepository quizOngoingRepository;
    private final AccountRepository accountRepository;
    private final ScoreRepository scoreRepository;
    private final ChapterRepository chapterRepository;

    // 퀴즈 5문제
    public List<QuizDetailDto> getQuiz(Long lessonId, Long accountId, HttpSession session) {
        // 퀴즈 조회
        List<Quiz> quizzes = quizRepository.findByLessonId(lessonId);

        System.out.println(quizzes.size() );
        Collections.shuffle(quizzes);
        List<Quiz> selected = quizzes.stream().limit(5).toList();
        // 진행 중인 세션이 없다면 새로 생성
        if(quizOngoingRepository.findByAccountIdAndLessonId(accountId, lessonId).isEmpty()){
            QuizOngoing ongoing = new QuizOngoing();
            ongoing.setAccount(accountRepository.findById(accountId).orElseThrow());
            ongoing.setLesson(lessonRepository.findById(lessonId).orElseThrow());
            ongoing.setCorrectNum(0);
//            quizOngoingRepository.save(ongoing);
        }

        List<QuizDetailDto> dtoList = selected.stream().map(this::toDto).toList();
        session.setAttribute("quizList_lesson_" + lessonId + "_" + accountId, dtoList);
        return dtoList;

    }
    // 대단원 시험 (20문제)
    public List<QuizDetailDto> getChapterQuiz(Long chapterId, Long accountId, HttpSession session) {
        // 1. 해당 챕터의 소단원들 가져오기
        List<Lesson> lessons = lessonRepository.findByChapter_Id(chapterId);

        // 2. 각 소단원에서 퀴즈 모으기
        List<Quiz> quizzes = lessons.stream()
                .flatMap(lesson -> quizRepository.findByLessonId(lesson.getId()).stream())
                .toList();
        // 랜덤하게 20개 선택
        Collections.shuffle(quizzes);
        List<Quiz> selected = quizzes.stream().limit(20).toList();

        // 3. 진행 중 기록 없으면 QuizOngoing 생성
        boolean hasOngoing = lessons.stream()
                .anyMatch(lesson -> quizOngoingRepository
                        .findByAccountIdAndLessonId(accountId, lesson.getId())
                        .isPresent());

        if (!hasOngoing && !lessons.isEmpty()) {
            Lesson firstLesson = lessons.get(0); // 대표 lesson 사용
            QuizOngoing ongoing = new QuizOngoing();
            ongoing.setAccount(accountRepository.findById(accountId).orElseThrow());
            ongoing.setLesson(firstLesson); // ✅ chapter 저장 안 함
            ongoing.setCorrectNum(0);
            quizOngoingRepository.save(ongoing);
        }

        // 4. DTO 변환 후 세션 저장
        List<QuizDetailDto> dtoList = selected.stream().map(this::toDto).toList();
        session.setAttribute("quizList_chapter_" + chapterId + "_" + accountId, dtoList);

        return dtoList;
    }
    private QuizDetailDto toDto(Quiz quiz) {
        QuizDetailDto dto = new QuizDetailDto();
        dto.setQuizId(quiz.getId());
        dto.setQuizType(quiz.getQuizType());
        dto.setContextJson(quiz.getContextJson());
        return dto;
    }

    // 단일 문제 채점
    public QuizGradedDto gradeQuiz(QuizAnsweredDto quizAnsweredDto, Long lessonId, Long accountId) {
        Quiz quiz = quizRepository.findById(quizAnsweredDto.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException("문제가 없습니다."));
        boolean isCorrect = quiz.getAnswer().equals(quizAnsweredDto.getStudentAnswer());
        // QuizOngoing correctNum 증가
        QuizOngoing ongoing = quizOngoingRepository.findByAccountIdAndLessonId(accountId, lessonId)
                .orElseThrow(() -> new IllegalStateException("진행 중인 퀴즈가 없습니다."));

        if (isCorrect) {
            ongoing.setCorrectNum(ongoing.getCorrectNum() + 1);
//            quizOngoingRepository.save(ongoing);
        }

        // 채점 결과 반환
        QuizGradedDto result = new QuizGradedDto();
        result.setQuizId(quiz.getId());
        result.setCorrect(isCorrect);
        result.setAnswer(quiz.getAnswer());
        result.setSolution(quiz.getSolution());
        return result;
        }
        // 퀴즈 종료 및 점수 반환
        public int endQuiz(Long accountId, Long lessonId){
            QuizOngoing ongoing = quizOngoingRepository.findByAccountIdAndLessonId(accountId,lessonId)
                    .orElseThrow(() -> new IllegalStateException("진행 중 퀴즈가 없습니다."));

            int correctNum = ongoing.getCorrectNum(); // 맞힌 개수

            // 퀴즈 진도율 계산
            int progressRate = Math.min(correctNum*20, 100); // 한 문제당 20점, 100점초과 방지
            // Score 엔티티 가져오기 또는 새로 생성
            Score score = scoreRepository.findByAccountIdAndLessonId(accountId,lessonId)
                    .orElseGet(() -> {
                        Score newScore = new Score();
                        newScore.setAccount(accountRepository.findById(accountId).orElseThrow());
                        newScore.setLesson(lessonRepository.findById(lessonId).orElseThrow());
                        return newScore;
                    });

            // 진도율 저장
            score.setCompleteRate(progressRate);
            score.setAttemptCount(score.getAttemptCount()+1);
//            scoreRepository.save(score);


//            quizOngoingRepository.delete(ongoing); // 세션 삭제

            return correctNum; // 화면에 맞은 개수 띄울때

        }

}
