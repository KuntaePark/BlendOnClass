package com.blendonclass.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Getter@Setter
public class Progress {
    @Id
    @Column(name="progress_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

private long id;

    @ColumnDefault("now()")
    private LocalDate startDate;//진도 시작 날짜
    private LocalDate endDate;//진도 종료 날짜

    @ManyToOne
    @JoinColumn(name="lesson_id")
    @Column(name="start_les_id")
    private Lesson startLesson;//진도 범위의 시작 소단원

    @ManyToOne
    @JoinColumn(name="lesson_id")
    @Column(name="end_les_id")
    private Lesson endLesson;//진도 범위의 끝 소단원

    @ManyToOne
    @JoinColumn(name="classroom_id")
    private Classroom classroom;

}
