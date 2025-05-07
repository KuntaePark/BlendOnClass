package com.blendonclass.dto;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.entity.Chapter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ScoreDataDto {
    private String chapterTitle;
    private int grade;
    private SUBJECT subject;
    private List<ScoreUnit> scoreUnits;

    public static ScoreDataDto of(Chapter chapter, List<ScoreUnit> scoreUnits) {
        ScoreDataDto scoreDataDto = new ScoreDataDto();
        scoreDataDto.setChapterTitle(chapter.getTitle());
        scoreDataDto.setGrade(chapter.getGrade());
        scoreDataDto.setSubject(chapter.getSubject());
        scoreDataDto.setScoreUnits(scoreUnits);
        return scoreDataDto;
    }
}
