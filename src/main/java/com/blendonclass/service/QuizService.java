package com.blendonclass.service;

import com.blendonclass.dto.QuizAnsweredDto;
import com.blendonclass.dto.QuizDetailDto;
import com.blendonclass.dto.QuizGradedDto;
import com.blendonclass.entity.Lesson;
import com.blendonclass.entity.Quiz;
import com.blendonclass.entity.QuizOngoing;
import com.blendonclass.entity.Score;
import com.blendonclass.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final LessonRepository lessonRepository;
    private final QuizOngoingRepository quizOngoingRepository;
    private final AccountRepository accountRepository;
    private final ScoreRepository scoreRepository;


    // 퀴즈 5문제
    public List<QuizDetailDto> getQuiz(Long lessonId, Long accountId, HttpSession session) {
        List<Quiz> quizzes = quizRepository.findByLessonId(lessonId);

        if(quizzes == null || quizzes.isEmpty()){
            return Collections.emptyList(); // 퀴즈 없으면 아무것도 안함
        }

        Collections.shuffle(quizzes);
        List<Quiz> selected = quizzes.stream().limit(5).toList();

        if(quizOngoingRepository.findByAccountIdAndLessonId(accountId, lessonId).isEmpty()){
            QuizOngoing ongoing = new QuizOngoing();
            ongoing.setAccount(accountRepository.findById(accountId).orElseThrow());
            ongoing.setLesson(lessonRepository.findById(lessonId).orElseThrow());
            ongoing.setCorrectNum(0);
            quizOngoingRepository.save(ongoing);
        }

        List<QuizDetailDto> dtoList = selected.stream().map(this::toDto).toList();
        session.setAttribute("quizList_lesson_" + lessonId + "_" + accountId, dtoList);
        return dtoList;
    }

    // 대단원 시험 (20문제)
    public List<QuizDetailDto> getChapterQuiz(Long chapterId, Long accountId, HttpSession session) {
        List<Lesson> lessons = lessonRepository.findByChapter_Id(chapterId);
        List<Quiz> quizzes = lessons.stream()
                .flatMap(lesson -> quizRepository.findByLessonId(lesson.getId()).stream())
                .filter(q -> q.getId() <= 8) //  JSON에 실제 존재하는 문제만 포함 >> 테스트 후에 지우기!!!!!!!!!
                .collect(Collectors.toList());

        if (quizzes == null || quizzes.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(quizzes);
        List<Quiz> selected = quizzes.stream().limit(20).toList();

        boolean hasOngoing = lessons.stream()
                .anyMatch(lesson -> quizOngoingRepository
                        .findByAccountIdAndLessonId(accountId, lesson.getId())
                        .isPresent());

        if (!hasOngoing && !lessons.isEmpty()) {
            Lesson firstLesson = lessons.get(0);
            QuizOngoing ongoing = new QuizOngoing();
            ongoing.setAccount(accountRepository.findById(accountId).orElseThrow());
            ongoing.setLesson(firstLesson);
            ongoing.setCorrectNum(0);
            quizOngoingRepository.save(ongoing);
        }

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

        QuizOngoing ongoing = quizOngoingRepository.findByAccountIdAndLessonId(accountId, lessonId)
                .orElseThrow(() -> new IllegalStateException("진행 중인 퀴즈가 없습니다."));

        if (isCorrect) {
            ongoing.setCorrectNum(ongoing.getCorrectNum() + 1);
            quizOngoingRepository.save(ongoing);
        }

        QuizGradedDto result = new QuizGradedDto();
        result.setQuizId(quiz.getId());
        result.setCorrect(isCorrect);
        result.setAnswer(quiz.getAnswer());
        result.setSolution(quiz.getSolution());
        return result;
    }

    // 퀴즈 종료 및 점수 반환
    public int endQuiz(Long accountId, Long lessonId) {
        QuizOngoing ongoing = quizOngoingRepository.findByAccountIdAndLessonId(accountId, lessonId)
                .orElseThrow(() -> new IllegalStateException("진행 중 퀴즈가 없습니다."));

        int correctNum = ongoing.getCorrectNum();
        int progressRate = Math.min(correctNum * 20, 100);

        Score score = scoreRepository.findByAccountIdAndLessonId(accountId, lessonId)
                .orElseGet(() -> {
                    Score newScore = new Score();
                    newScore.setAccount(accountRepository.findById(accountId).orElseThrow());
                    newScore.setLesson(lessonRepository.findById(lessonId).orElseThrow());
                    newScore.setCompleteRate(progressRate); // 첫 시도에만 저장
                    newScore.setAttemptCount(0);
                    return newScore;
                });

        // 기존 시도라면 attemptCount만 증가, completeRate는 유지
        if (score.getAttemptCount() > 0) {
            score.setAttemptCount(score.getAttemptCount() + 1);
        } else {
            score.setAttemptCount(1);
        }

        scoreRepository.save(score);
        quizOngoingRepository.delete(ongoing);
        return correctNum;
    }


    public void exitOngoingQuiz(Long accountId, Long lessonId) {
        quizOngoingRepository.findByAccountIdAndLessonId(accountId, lessonId)
                .ifPresent(quizOngoingRepository::delete);
    }


    public Long getFirstLessonIdOfChapter(Long chapterId) {
        List<Lesson> lessons = lessonRepository.findByChapter_Id(chapterId);
        if(lessons.isEmpty()) {
            throw new IllegalStateException("해당 챕터에 소단원이 없습니다.");
        }
        return lessons.get(0).getId();
    }
}
