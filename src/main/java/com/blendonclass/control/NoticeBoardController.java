package com.blendonclass.control;

import com.blendonclass.dto.NoticeShowDto;
import com.blendonclass.dto.NoticeWriteDto;
import com.blendonclass.service.board.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    //공지 게시글 삭제
    @PostMapping("/notice/delete")
    public String deleteNotice(@RequestParam("id") Long nbId, Principal principal, Model model){
        //지금 로그인되어있는 사람 db에서 기본키값

        noticeBoardService.deleteNotice(nbId);
        return "redirect:/student";
    }
    
    //공지 작성 페이지 요청
    @GetMapping("/notice/detail")
    public String noticeDetail(@RequestParam("id") Long nbId, Principal principal, Model model){
        Long accountId = Long.parseLong(principal.getName());
        NoticeShowDto noticeShowDto = noticeBoardService.getNoticeDetail(nbId, accountId);
        model.addAttribute("noticeShowDto", noticeShowDto);
        return "board/noticeDetail";
    }

    //공지 작성 페이지 요청
    @GetMapping("/notice/write")
    public String getNoticeWritePage(@RequestParam("id") Long classroomId, Model model){
        NoticeWriteDto noticeWriteDto = new NoticeWriteDto();
        model.addAttribute("noticeWriteDto", noticeWriteDto);
        model.addAttribute("classroomId", classroomId);
        return "board/noticeWrite";
    }

    //공지 저장
    @PostMapping("/notice/write")
    public String saveNotice(NoticeWriteDto noticeWriteDto,
                       @RequestParam("id") Long classroomId,
                       Principal principal,
                       @RequestParam("fileUrl") MultipartFile multipartFile,
                        Model model){
        Long id = Long.parseLong(principal.getName());
        noticeWriteDto.setWriterId(id);
        noticeWriteDto.setClassroomId(classroomId);
        noticeBoardService.saveNotice(noticeWriteDto, multipartFile);
        model.addAttribute("classroomId", classroomId);
        return "redirect:/board?id="+classroomId;
    }

}
