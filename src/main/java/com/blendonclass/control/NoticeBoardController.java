package com.blendonclass.control;

import com.blendonclass.dto.NoticeWriteDto;
import com.blendonclass.service.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NoticeBoardController {
    @Autowired
    private NoticeBoardService noticeBoardService;

    public String saveNotice(NoticeWriteDto noticeWriteDto, MultipartFile multipartFile, Model model){
        return null;
    }

    public String deleteNotice(@RequestParam("id") Long nbId, Model model){
        return null;
    }

    public String getNoticeDetail(@RequestParam("id") Long nbId, Model model){
        return null;
    }
}
