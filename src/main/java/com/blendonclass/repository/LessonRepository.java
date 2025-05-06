package com.blendonclass.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.blendonclass.entity.Chapter;
import com.blendonclass.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
     List<Lesson> findByChapter(Chapter chapter);


    List<Lesson> findByChapter_Id(Long chapId);

    Lesson findFirstByOrderByIdAsc();


    Optional<Lesson> findTopByIdLessThanOrderByIdDesc(Long id);

    Optional<Lesson> findTopByIdGreaterThanOrderByIdAsc(Long id);
}
