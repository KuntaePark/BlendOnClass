package com.blendonclass.control;

import com.blendonclass.dto.NoticeWriteDto;
import com.blendonclass.service.board.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    public String deleteNotice(@RequestParam("id") Long nbId, Principal principal, Model model){
        //지금 로그인되어있는 사람 db에서 기본키값
        Long id = Long.parseLong(principal.getName());
        return null;
    }

    public String getNoticeDetail(@RequestParam("id") Long nbId, Model model){
        return null;
    }

    @GetMapping("/post/notice")
    public String notice(){

        return "notice";
    }

    @PostMapping("/post/notice")
    public String save(@ModelAttribute NoticeWriteDto noticeWriteDto, MultipartFile multipartFile){
        noticeBoardService.saveNotice(noticeWriteDto, multipartFile);
        return "redirect:/student";
    }
}
