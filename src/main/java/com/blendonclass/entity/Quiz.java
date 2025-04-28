package com.blendonclass.entity;

import com.blendonclass.constant.QTYPE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="quiz_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private QTYPE  quizType;

    @Column(columnDefinition = "TEXT")
    private String contextJson;

    @Column(columnDefinition = "TEXT")
    private String solution;

    private String answer;

    private int difficulty;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
