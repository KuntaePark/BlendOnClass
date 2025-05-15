package com.blendonclass.entity;

import com.blendonclass.dto.QuestionWriteDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class QuestionBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qb_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String qContext;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime qWriteTime;

    @Column(columnDefinition = "TEXT")
    private String aContext;

    private LocalDateTime aWriteTime;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "answerer_id", referencedColumnName = "account_id")
    private Account account;

    public static QuestionBoard toQuestionSaveBoard(QuestionWriteDto questionWriteDto, Authority authority, Account account) {
        QuestionBoard questionBoard = new QuestionBoard();
        //questionBoard.setId(questionWriteDto.getId());
        questionBoard.setTitle(questionWriteDto.getTitle());
        questionBoard.setQContext(questionWriteDto.getQContext());
        questionBoard.setAuthority(authority);
        questionBoard.setAccount(account);
        return questionBoard;
    }
}
