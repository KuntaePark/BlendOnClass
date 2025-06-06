package com.blendonclass.entity;

import com.blendonclass.dto.board.NoticeWriteDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@EntityListeners(AuditingEntityListener.class)
public class NoticeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nb_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String context;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writeTime;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Authority authority;

    public static NoticeBoard toNoticeBoard(NoticeWriteDto noticeWriteDto, Authority authority) {
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setId(noticeWriteDto.getNbId());
        noticeBoard.setTitle(noticeWriteDto.getTitle());
        noticeBoard.setContext(noticeWriteDto.getContext());
        noticeBoard.setAuthority(authority);
        return noticeBoard;
    }
}
