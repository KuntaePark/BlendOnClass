package com.blendonclass.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Stat {
    @Id
    private String id;

    private Long stat;

    public void addAccount() {
        this.stat++;
    }
}
