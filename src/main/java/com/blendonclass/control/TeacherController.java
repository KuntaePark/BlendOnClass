package com.blendonclass.control;


import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.AlarmListDto;
import com.blendonclass.dto.ClassroomListDto;
import com.blendonclass.dto.ProgressListDto;
import com.blendonclass.dto.admin.ClassroomDto;
import com.blendonclass.entity.Classroom;
import com.blendonclass.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {
    private final AuthorityService authorityService;
    private final ProgressService progressService;
    private final AlarmService alarmService;
    private final ClassroomService classroomService;

    @GetMapping(value={"", "{id}"})
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

        //선택 반 정보 넣기
        ClassroomDto classroomDto = classroomService.getClassroomById(curClassroomId);

        model.addAttribute("classroomListDtos", classroomListDtos);
        model.addAttribute("classroomDto", classroomDto);
        model.addAttribute("alarmListDtos", alarmListDtos);
        return "teacherMain";
    }

    @GetMapping("/classrooms")
    public String getTeacherClassrooms(
            @RequestParam("accountId") Long accountId,
            @RequestParam(value = "subjects", required = false)List<SUBJECT> subjects, Model model) {
        // 테스트용 subjects 기본값 (HR, ENG)
        if (subjects == null || subjects.isEmpty()) {
            subjects = Arrays.asList(SUBJECT.HR, SUBJECT.ENG);
        }

        List<Classroom> classrooms = authorityService.getClassroomsByAccountIdAndSubjects(accountId, subjects);


        model.addAttribute("accountId", accountId);
        model.addAttribute("subjects", subjects);
        model.addAttribute("classrooms", classrooms);

        return "teacher/classroom-list"; // templates/teacher/classroom-list.html
    }
}

