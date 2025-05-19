package com.blendonclass.repository;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonClassroomScoreDto;
import com.blendonclass.dto.ProgRateCalcDto;
import com.blendonclass.entity.ClassroomScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomScoreRepository extends JpaRepository<ClassroomScore, Long> {

    ClassroomScore findByLessonIdAndClassroomId(Long lessonId, Long classroomId);


//    List<LessonScoreDto> findScoresByGradeAndSubjectAndClassroomId(int grade, SUBJECT subject, Long classroomId);
    List<ClassroomScore> findByClassroomIdOrderByLessonIdAsc(Long classroomId);
    List<ClassroomScore> findByClassroomIdAndLessonIdBetweenOrderByLessonIdAsc(Long classroomId, Long lessonId1, Long lessonId2);

    @Query("""
        select cs.completeRate
        from ClassroomScore cs
        right join cs.lesson l on cs.lesson.id = l.id and cs.classroom.id = :classroomId
        where l.id between :lessonId1 and :lessonId2
        """)
    List<Float> findCompleteRatesOfProgress(Long classroomId,Long lessonId1, Long lessonId2);


    @Query("""
    select new com.blendonclass.dto.LessonClassroomScoreDto(
        c.id, c.grade, c.subject, c.title, l.id, l.lessonTitle,
            l.lessonType, cs.completeRate, cs.attemptCount
        )
            from Chapter c
                         join Lesson l on l.chapter = c
                         left join ClassroomScore cs on cs.lesson = l and cs.classroom.id = :classroomId
                         where c.grade = :grade and c.subject = :subject
                         order by l.id asc
    """)
    List<LessonClassroomScoreDto> findScoresByGradeAndSubjectAndAccountId(int grade, SUBJECT subject, Long classroomId);
}
