package com.blendonclass.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProgressController {

    @GetMapping("/setprogress")
    public String getProgressSetPage(@RequestParam("id") Long classroomId, Model model) {

        return "setprogress";
    }
}
