package com.blendonclass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="score_id")
    private Long id;

    @ColumnDefault("0")
    private int completeRate;// 해당 소단원의 진도 완수율 진도 기간 내 완수했을 시만 갱신

    @ColumnDefault("0")
    private int attemptCount;// 시도횟수, 진도기간 내 시도했을 시 만 갱신

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;// 해당 계정

    @ManyToOne
    @JoinColumn(name="lesson_id")
    private Lesson lesson;// 해당 소단원


}
