package com.blendonclass.control;

import com.blendonclass.dto.NoticeShowDto;
import com.blendonclass.dto.NoticeWriteDto;
import com.blendonclass.service.board.NoticeBoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String deleteNotice(@RequestParam("id") Long nbId, @RequestParam("classroomId") Long classroomId, Principal principal, Model model){
        noticeBoardService.deleteNotice(nbId);
        return "redirect:/board?id=" + classroomId;
    }
    
    //공지 작성 페이지 요청
    @GetMapping("/notice/detail")
    public String noticeDetail(@RequestParam("id") Long nbId, @RequestParam("classroomId") Long classroomId, Principal principal, Model model){
        Long accountId = Long.parseLong(principal.getName());
        NoticeShowDto noticeShowDto = noticeBoardService.getNoticeDetail(nbId, accountId);
        model.addAttribute("noticeShowDto", noticeShowDto);
        model.addAttribute("classroomId", classroomId);
        return "board/noticeDetail";
    }

    //공지 작성 페이지 요청
    @GetMapping("/notice/write")
    public String getNoticeWritePage(@RequestParam(name="id",required = false) Long nbId,
                                     @RequestParam("classroomId") Long classroomId,
                                     Model model){
        NoticeWriteDto noticeWriteDto = null;
        if(nbId != null){
            //수정
            noticeWriteDto = noticeBoardService.getNoticeWriteDto(nbId);
            System.out.println(noticeWriteDto);
        } else {
            noticeWriteDto = new NoticeWriteDto();
        }
        model.addAttribute("noticeWriteDto", noticeWriteDto);
        model.addAttribute("classroomId", classroomId);
        return "board/noticeWrite";
    }

    //공지 저장
    @PostMapping("/notice/write")
    public String saveNotice(@Valid NoticeWriteDto noticeWriteDto,
                       BindingResult bindingResult,
                       @RequestParam("id") Long classroomId,
                       Principal principal,
                       @RequestParam("file") MultipartFile multipartFile,
                        Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("noticeWriteDto", noticeWriteDto);
            model.addAttribute("classroomId", classroomId);
            return "board/noticeWrite";
        }
        Long id = Long.parseLong(principal.getName());
        noticeWriteDto.setWriterId(id);
        noticeWriteDto.setClassroomId(classroomId);
        noticeBoardService.saveNotice(noticeWriteDto, multipartFile);
        model.addAttribute("classroomId", classroomId);
        return "redirect:/board?id="+classroomId;
    }

}
