package com.blendonclass.repository;

import com.blendonclass.entity.LessonRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRecordRepository extends JpaRepository<LessonRecord, Long> {
    LessonRecord findTopByAccountIdOrderByIdDesc(Long accountId);
}
