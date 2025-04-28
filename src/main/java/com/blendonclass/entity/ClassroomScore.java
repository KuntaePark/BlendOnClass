package com.blendonclass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
public class ClassroomScore {
    @Id
    @Column(name="cs_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault("0")
    private float completeRate;//반 학생들 진도율 합. 학생수로 나눠서 평균계산
    @ColumnDefault("0")
    private float attemptCount;//반 학생들 시도횟수 합. 학생수로 나눠서 평균계산

    @ManyToOne
    @JoinColumn(name="classroom_id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name="lesson_id")
    private Lesson lesson;
}
