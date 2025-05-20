package com.blendonclass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter@Setter
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classroom_id")
    private Long id;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private int classroomNum;

    @ColumnDefault("0")
    private int studentCount;

    public void dropStudent(){
        this.studentCount--;

    }

    public void addStudent() {
        this.studentCount++;
    }
}
