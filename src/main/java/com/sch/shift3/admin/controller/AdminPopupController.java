package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.PopupService;
import com.sch.shift3.user.dto.PopupDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/popup")
public class AdminPopupController {
    private final PopupService popupService;

    @Transactional
    @PostMapping("/create.do")
    public ResponseEntity<String> createPopup(@ModelAttribute PopupDto popupDto){
        popupService.createPopup(popupDto);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PostMapping("/edit.do")
    public ResponseEntity<String> editPopup(@ModelAttribute PopupDto popupDto){
        popupService.editPopup(popupDto);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @GetMapping("/remove.do")
    public void deletePopup(@RequestParam Integer id, HttpServletResponse response){
        popupService.deletePopup(id);

        try {
            response.sendRedirect("/admin/popup/list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
