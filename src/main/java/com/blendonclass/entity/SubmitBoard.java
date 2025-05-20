package com.blendonclass.entity;

import com.blendonclass.dto.board.AssignmentShowDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@EntityListeners(AuditingEntityListener.class)
public class SubmitBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sb_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String context;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writeTime;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "ab_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AssignmentBoard assignmentBoard;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    public static SubmitBoard from(AssignmentShowDto assignmentShowDto, Account account, AssignmentBoard assignmentBoard) {
        SubmitBoard submitBoard = new SubmitBoard();
        submitBoard.setContext(assignmentShowDto.getContext());
        submitBoard.setAssignmentBoard(assignmentBoard);
        submitBoard.setAccount(account);
        return submitBoard;
    }
}
