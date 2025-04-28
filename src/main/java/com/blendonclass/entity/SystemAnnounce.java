package com.blendonclass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class SystemAnnounce {
    @Id
    @Column(name="sa_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;//제목
    @Column(columnDefinition = "TEXT")
    private String context;//내용
    private LocalDateTime writeTime;//작성시간



}
