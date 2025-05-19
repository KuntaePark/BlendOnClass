package com.blendonclass.entity;

import com.blendonclass.constant.SUBJECT;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter@Setter
@ToString
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    SUBJECT authType;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}

