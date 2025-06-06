package com.blendonclass.dto;

import com.blendonclass.entity.Chapter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ChapterDto {
    private Long chapId;

    private String title;
    private List<LessonDto> lessonDtoList;

    public static ChapterDto from(Chapter chapter, List<LessonDto> lessonDtoList) {
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setChapId(chapter.getId());
        chapterDto.setTitle(chapter.getTitle());
        chapterDto.setLessonDtoList(lessonDtoList);

        return chapterDto;
    }
}
