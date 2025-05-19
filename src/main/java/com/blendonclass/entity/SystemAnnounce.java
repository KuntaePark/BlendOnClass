package com.blendonclass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@EntityListeners(AuditingEntityListener.class)
public class SystemAnnounce {
    @Id
    @Column(name="sa_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;//제목
    @Column(columnDefinition = "TEXT")
    private String context;//내용
    @CreatedDate
    @LastModifiedDate
    private LocalDateTime writeTime;//작성시간



}
