package com.blendonclass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter @Setter
public class QuizOngoing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qo_id")
    private Long id;

    @ColumnDefault("0")
    private int correctNum;

    @OneToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
