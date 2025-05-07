package com.blendonclass.entity;


import com.blendonclass.constant.SUBJECT;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@EntityListeners(AuditingEntityListener.class)
public class AuthRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ar_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SUBJECT authType;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime reqTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
