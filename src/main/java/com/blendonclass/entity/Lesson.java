package com.blendonclass.entity;

import com.blendonclass.constant.LTYPE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter @Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("NORMAL")
    private LTYPE lessonType;

    @Column(nullable = false)
    private String lessonTitle;

    @Column(nullable = false)
    private String lessonBrief;

    @ManyToOne
    @JoinColumn(name="chap_id")
    private Chapter chapter;

}
