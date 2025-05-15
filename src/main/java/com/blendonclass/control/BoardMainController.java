package com.blendonclass.control;

import com.blendonclass.constant.ROLE;
import com.blendonclass.dto.AssignmentShowDto;
import com.blendonclass.dto.NoticeShowDto;
import com.blendonclass.dto.QuestionShowDto;
import com.blendonclass.service.AuthorityService;
import com.blendonclass.service.CustomUserDetails;
import com.blendonclass.service.board.AssignmentBoardService;
import com.blendonclass.service.board.NoticeBoardService;
import com.blendonclass.service.board.QuestionBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardMainController {
    private final NoticeBoardService noticeBoardService;
    private final QuestionBoardService questionBoardService;
    private final AssignmentBoardService assignmentBoardService;
    private final AuthorityService authorityService;
    

    @GetMapping("")
    public String getClassroomBoardPage(@RequestParam(name = "id",required = false) Long classroomId,
                                        Principal principal,
                                        Model model){
        //todo - 학생인지 체크
        if(classroomId == null){
            //학생이므로 소속반 불러오기
            Long id = Long.parseLong(principal.getName());
            classroomId = authorityService.getClassroomIdOfStudent(id);
        }
        //불러올 페이지 표시 0 -> 첫 페이지, pageSize -> 10개 불러오겠다
        Pageable pageable = PageRequest.of(0, 10);
        
        //페이지를 가져오는 거
        Page<NoticeShowDto> noticeShowDtos = noticeBoardService.getNoticeList(pageable, classroomId);
        model.addAttribute("noticeShowDtos", noticeShowDtos);

        //todo - 나머지 2개 assignmentBoard, QuestionBoard도 똑같이 불러와야 됨
        Page<AssignmentShowDto> assignmentShowDtos = assignmentBoardService.getAssignmentList(pageable, classroomId);
        model.addAttribute("assignmentShowDtos", assignmentShowDtos);

        Page<QuestionShowDto> questionShowDtos = questionBoardService.getQuestionList(pageable, classroomId);
        model.addAttribute("questionShowDtos", questionShowDtos);
        model.addAttribute("classroomId", classroomId);
        return "post";
    }

}
