package com.blendonclass.repository.board;

import com.blendonclass.entity.QuestionBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {
    @Query("""
        select qb
        from QuestionBoard qb
        join Authority a on qb.authority = a
        where a.classroom.id = :classroomId
        order by qb.aWriteTime desc
    """)
    List<QuestionBoard> findQuestionBoardByClassroomId(@Param("classroomId") Long classroomId, Pageable pageable);
}
