package com.blendonclass.entity;

import com.blendonclass.constant.SUBJECT;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
@EntityListeners(AuditingEntityListener.class)
public class AssignmentBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ab_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String context;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writeTime;

    private LocalDate dueDate;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Authority authority;


}
