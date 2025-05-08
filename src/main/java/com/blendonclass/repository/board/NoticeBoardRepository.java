package com.blendonclass.repository.board;

import com.blendonclass.entity.NoticeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
    @Query("""
        select nb
        from NoticeBoard nb
        join Authority a on nb.authority = a
        where a.classroom.id = :classroomId
        order by nb.writeTime desc
    """)
    List<NoticeBoard> findNoticeBoardByClassroomId(@Param("classroomId") Long classroomId, Pageable pageable);

}
