package com.blendonclass.control;

import com.blendonclass.constant.ROLE;
import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.AlarmListDto;
import com.blendonclass.dto.ClassroomListDto;
import com.blendonclass.dto.ProgressListDto;
import com.blendonclass.dto.UserAccountDto;
import com.blendonclass.dto.admin.AccountDto;
import com.blendonclass.entity.Account;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Classroom;
import com.blendonclass.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Getter@Setter
@RequiredArgsConstructor
public class MainController {
    private final AccountService accountService;
    private final AuthorityService authorityService;
    private final ProgressService progressService;
    private final AlarmService alarmService;


    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        ROLE role = (ROLE)session.getAttribute("role");
        String redirectUrl = switch (role) {
            case ADMIN -> "/admin";
            case TEACHER -> "/teacher";
            case STUDENT -> "/student";
        };
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/student")
    public String student(Model model) {return "redirect:/student/main";}

    @GetMapping(value={"/teacher", "/teacher/{id}"})
    public String teacher(@PathVariable("id") Optional<Long> classroomId, Principal principal, Model model) {
        Long id = Long.parseLong(principal.getName());
        List<ClassroomListDto> classroomListDtos = authorityService.getClassroomsByAccountId(id);

        Long curClassroomId = classroomId.isPresent() ? classroomId.get() : classroomListDtos.get(0).getClassroomId();

        //해당 반 알림 로드
        List<AlarmListDto> alarmListDtos1 = alarmService.getAlarmByAccountIdAndClassroomId(id, curClassroomId);
        //시스템 공지
        List<AlarmListDto> alarmListDtos2 = alarmService.getSystemAlarm(id);
        List<AlarmListDto> alarmListDtos = Stream.concat(alarmListDtos1.stream(), alarmListDtos2.stream()).toList();

        //반 및 권한 목록을 가지고 있으므로, 해당 반에 대해 담임 권한이 있다면 전체 과목 진도를 가져옴
        List<ClassroomListDto> curClassroomListDtos = classroomListDtos.stream().filter(
                classroomListDto -> classroomListDto.getClassroomId().equals(curClassroomId))
                .collect(Collectors.toList());

        //진도율 모두 검색
        List<ProgressListDto> progressListDtos = progressService.getProgressesOfClassroom(curClassroomId);
        for(ProgressListDto progressListDto : progressListDtos) {
            System.out.println(progressListDto);
        }
        if(curClassroomListDtos.stream().anyMatch(classroomListDto ->
                classroomListDto.getSubject().equals("담임"))) {
            model.addAttribute("progressListDtos", progressListDtos);
        } else {
            //해당 과목만
            ClassroomListDto classroomListDto = curClassroomListDtos.get(0);
            progressListDtos = progressListDtos.stream().filter(progressListDto ->
                    progressListDto.getSubject().getSubject().equals(classroomListDto.getSubject())).collect(Collectors.toList());
            model.addAttribute("progressListDtos", progressListDtos);
        }

        model.addAttribute("classroomListDtos", classroomListDtos);
        model.addAttribute("classroomId",curClassroomId);
        model.addAttribute("alarmListDtos", alarmListDtos);
        return "teacherMain";
    }


    @GetMapping("/admin")
    public String admin(Model model) {return "redirect:/admin/accounts";}

    @GetMapping("/myInfo")
    public String myInfo(Principal principal, Model model) {
        Long id = Long.parseLong(principal.getName());
        UserAccountDto userAccountDto = accountService.getUserAccountInfo(id);

        model.addAttribute("userAccountDto", userAccountDto);
        return "myInfo";
    }

    @PostMapping("/myInfo")
    public String saveInfo(@Valid UserAccountDto userAccountDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "myInfo";
        }
        if(!userAccountDto.getPassword().equals(userAccountDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch","비밀번호가 일치하지 않습니다.");
            return "myInfo";
        }
        //email 중복 체크
        if(accountService.checkEmail(userAccountDto)) {
            bindingResult.rejectValue("email", "email.overlap","이메일이 중복입니다.");
            return "myInfo";
        }
        accountService.saveUserAccount(userAccountDto);
        return "redirect:/";
    }
}