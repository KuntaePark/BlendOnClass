package com.blendonclass.entity;

import com.blendonclass.constant.SUBJECT;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class AuthRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ar_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SUBJECT authType;

    @Column(nullable = false)
    @ColumnDefault("now()")
    private LocalDateTime reqTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
