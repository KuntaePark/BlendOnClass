package com.blendonclass.constant;

import lombok.Getter;

@Getter
public enum SUBJECT {
    HR("담임"), MATH("수학"), ENG("영어"), KOR("국어");

    private final String subject;
    private SUBJECT(String subject) {this.subject = subject;}
}
