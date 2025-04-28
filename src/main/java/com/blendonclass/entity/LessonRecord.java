package com.blendonclass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LessonRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lr_id")

    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @JoinColumn(name="lesson_id")
    private Lesson lesson;

}
