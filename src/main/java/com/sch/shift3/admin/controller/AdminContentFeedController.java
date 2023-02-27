package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.AdminContentFeedService;
import com.sch.shift3.user.dto.ContentFeedDto;
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
@RequestMapping("/admin/content")
public class AdminContentFeedController {

    private final AdminContentFeedService adminContentFeedService;

    @Transactional
    @PostMapping("/create.do")
    public ResponseEntity<String> createProduct(@ModelAttribute ContentFeedDto contentFeedDto){
        log.info("contentFeedDto: {}", contentFeedDto);

        // create feed
        adminContentFeedService.createContentFeed(contentFeedDto);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PostMapping("/edit.do")
    public ResponseEntity<String> editProduct(@ModelAttribute ContentFeedDto contentFeedDto){
        log.info("contentFeedDto: {}", contentFeedDto);

        // edit feed
        adminContentFeedService.editContentFeed(contentFeedDto);

        return ResponseEntity.noContent().build();
    }


    @Transactional
    @GetMapping("/remove.do")
    public void deleteContents(@RequestParam Integer id, HttpServletResponse response){
        adminContentFeedService.deleteContentFeed(id);

        try {
            response.sendRedirect("/admin/contents/list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
