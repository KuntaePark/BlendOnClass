package com.blendonclass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LessonDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ld_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String context;

    private String mediaUrl;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
