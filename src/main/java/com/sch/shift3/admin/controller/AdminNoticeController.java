package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.AdminNoticeService;
import com.sch.shift3.user.dto.NoticeDto;
import com.sch.shift3.user.entity.Notice;
import com.sch.shift3.user.repository.NoticeRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class AdminNoticeController {
    private final AdminNoticeService adminNoticeService;
    private final NoticeRepository noticeRepository;

    @PostMapping("/create.do")
    public ResponseEntity<String> createNotice(@ModelAttribute NoticeDto noticeDto){
        if (noticeDto.getTitle().isBlank())
            throw new IllegalArgumentException("제목을 입력해주세요.");
        if (noticeDto.getDescription().isBlank())
            throw new IllegalArgumentException("내용을 입력해주세요.");

        Notice notice = Notice.builder()
                .title(noticeDto.getTitle())
                .description(noticeDto.getDescription())
                .build();

        noticeRepository.save(notice);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PostMapping("/edit.do")
    public ResponseEntity<String> editNotice(@ModelAttribute NoticeDto noticeDto){
        if (noticeDto.getId() == null)
            throw new IllegalArgumentException("해당 공지사항이 존재하지 않습니다.");

        Notice notice = adminNoticeService.findNoticeById(noticeDto.getId());
        if (notice == null)
            throw new IllegalArgumentException("해당 공지사항이 존재하지 않습니다.");

        notice.setTitle(noticeDto.getTitle());
        notice.setDescription(noticeDto.getDescription());

        noticeRepository.save(notice);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @GetMapping("/remove.do")
    public void removeNotice(@RequestParam Integer id, HttpServletResponse response){
        if (id == null)
            throw new IllegalArgumentException("해당 공지사항이 존재하지 않습니다.");

        Notice notice = adminNoticeService.findNoticeById(id);
        if (notice == null)
            throw new IllegalArgumentException("해당 공지사항이 존재하지 않습니다.");

        noticeRepository.delete(notice);
        try {
            response.sendRedirect("/admin/notice/list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
