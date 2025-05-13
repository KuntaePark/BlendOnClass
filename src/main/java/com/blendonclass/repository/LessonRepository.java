package com.blendonclass.repository;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ChapterLessonDto;
import com.blendonclass.entity.Chapter;
import com.blendonclass.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByChapter(Chapter chapter);

    List<Lesson> findByChapter_Id(Long chapId);

    Lesson findFirstByOrderByIdAsc();


    Optional<Lesson> findTopByIdLessThanOrderByIdDesc(Long id);

    Optional<Lesson> findTopByIdGreaterThanOrderByIdAsc(Long id);


    @Query("""
    select new com.blendonclass.dto.ChapterLessonDto(c.id, c.grade, c.subject,
        c.title, l.id, l.lessonTitle, l.lessonType)
    from Lesson l
    join l.chapter c
    where c.grade = :grade and c.subject = :subject
    """)
    List<ChapterLessonDto> findLessonsByGradeAndSubject(@RequestParam("grade")int grade,
                                                        @RequestParam("subject") SUBJECT subject);
}
