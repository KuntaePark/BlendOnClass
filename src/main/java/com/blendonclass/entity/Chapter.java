package com.blendonclass.entity;

import com.blendonclass.constant.SUBJECT;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chap_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SUBJECT subject;
}
