package com.blendonclass.control.admin;

/*
    공지 관리 페이지
 */

import com.blendonclass.dto.admin.SysAnnDetailDto;
import com.blendonclass.dto.admin.SysAnnListDto;
import com.blendonclass.repository.SystemAnnounceRepository;
import com.blendonclass.service.SysAnnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminAnnounceController {
    private final SysAnnService sysAnnService;

    @GetMapping("/admin/announce")
    public String getSysAnnList(@RequestParam("page")Optional<Integer> page,
                                Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8);
        Page<SysAnnListDto> sysAnnListDtos = sysAnnService.getSysAnnList(pageable);

        model.addAttribute("sysAnnListDtos", sysAnnListDtos);
        return "admin/announceMng";
    }

    @GetMapping("/admin/announce/write")
    public String getSysAnnWritePage(@RequestParam(name="id", required = false) Long saId,
                                    Model model) {
        SysAnnDetailDto sysAnnDetailDto = null;
        if(saId != null) {
            sysAnnDetailDto = sysAnnService.getSysAnnDetail(saId);
        } else {
            sysAnnDetailDto = new SysAnnDetailDto();
        }
        model.addAttribute("sysAnnDetailDto", sysAnnDetailDto);
        return "admin/announceWrite";
    }

    @PostMapping("/admin/announce/write")
    public String saveSysAnn(@Valid SysAnnDetailDto sysAnnDetailDto,
                             BindingResult bindingResult,
                            Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/announceWrite";
        }
        sysAnnService.saveSysAnnDetail(sysAnnDetailDto);
        return "redirect:/admin/announce";
    }

    @PostMapping("/admin/announce/delete")
    public String deleteSysAnn(@RequestParam("id") Long saId) {
        sysAnnService.deleteAnnounce(saId);
        return "redirect:/admin/announce";
    }

    //사용자 시스템 공지 열람용
    @GetMapping("/sysAnn")
    @ResponseBody
    public SysAnnDetailDto getSysAnnDetail(@RequestParam("id") Long saId) {
        return sysAnnService.getSysAnnDetail(saId);
    }
}
