package com.blendonclass.repository;

import com.blendonclass.entity.Chapter;
import com.blendonclass.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
     List<Lesson> findByChapter(Chapter chapter);

    List<Lesson> findByChapter_Id(Long chapId);

    Lesson findFirstByOrderByIdAsc();


    Optional<Lesson> findTopByIdLessThanOrderByIdDesc(Long id);

    Optional<Lesson> findTopByIdGreaterThanOrderByIdAsc(Long id);
}
