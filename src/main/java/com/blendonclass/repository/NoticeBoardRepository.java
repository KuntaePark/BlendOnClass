package com.blendonclass.repository;

import com.blendonclass.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
    List<NoticeBoard> findAllByClassroomIdOrderByWriteTimeDesc(Long classroomId, Pageable pageable);
    Optional<NoticeBoard> findById(Long nbId);
}
