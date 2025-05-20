package com.blendonclass.repository;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findByGradeAndSubject(int i, SUBJECT subject);
}
