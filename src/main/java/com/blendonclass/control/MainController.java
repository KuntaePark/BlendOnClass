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