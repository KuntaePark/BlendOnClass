package com.blendonclass.control.admin;

/*
    공지 관리 페이지
 */

import com.blendonclass.dto.admin.SysAnnListDto;
import com.blendonclass.repository.SystemAnnounceRepository;
import com.blendonclass.service.SysAnnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminAnnounceController {
    private final SysAnnService sysAnnService;

    @GetMapping("/admin/announce")
    public String getSysAnnList(@RequestParam("page")Optional<Integer> page,
                                Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<SysAnnListDto> sysAnnListDtos = sysAnnService.getSysAnnList(pageable);

        model.addAttribute("sysAnnListDtos", sysAnnListDtos);
        return "admin/announceMng";
    }
}
