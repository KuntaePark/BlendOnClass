package com.blendonclass.control;

import com.blendonclass.dto.NoticeShowDto;
import com.blendonclass.dto.NoticeWriteDto;
import com.blendonclass.service.board.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
public class NoticeBoardController {
    @Autowired
    private NoticeBoardService noticeBoardService;

    public String saveNotice(NoticeWriteDto noticeWriteDto, MultipartFile multipartFile, Model model){
        return null;
    }

    @PostMapping("/post/notice/detail")
    public String deleteNotice(@RequestParam("id") Long nbId, Principal principal, Model model){
        //지금 로그인되어있는 사람 db에서 기본키값

        noticeBoardService.deleteNotice(nbId);
        return "redirect:/student";
    }
    @GetMapping("/post/notice/detail")
    public String noticeDetail(@RequestParam("id") Long id, Model model){
        NoticeShowDto noticeShowDto = noticeBoardService.findById(id);
        model.addAttribute("noticeShowDto", noticeShowDto);
        return "noticeDetail";
    }

    @GetMapping("/post/notice")
    public String notice(@RequestParam("id") Long classroomId, Model model){
        NoticeWriteDto noticeWriteDto = new NoticeWriteDto();
        model.addAttribute("noticeWriteDto", noticeWriteDto);
        model.addAttribute("classroomId", classroomId);
        return "notice";
    }

    @PostMapping("/post/notice")
    public String noticeSave(NoticeWriteDto noticeWriteDto,
                       @RequestParam("id") Long classroomId,
                       Principal principal,
                       @RequestParam("fileUrl") MultipartFile multipartFile){
        Long id = Long.parseLong(principal.getName());
        noticeWriteDto.setWriterId(id);
        noticeWriteDto.setClassroomId(classroomId);
        System.out.println("writerId = " + noticeWriteDto.getWriterId());
        noticeBoardService.saveNotice(noticeWriteDto, multipartFile);
        return "redirect:/student";
    }

}
