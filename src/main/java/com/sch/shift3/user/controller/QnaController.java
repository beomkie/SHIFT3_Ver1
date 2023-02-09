package com.sch.shift3.user.controller;

import com.sch.shift3.config.CurrentUser;
import com.sch.shift3.user.dto.QnaRequest;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/mypage/qna")
@RequiredArgsConstructor
public class QnaController {
    private final QuestionService questionService;

    @PostMapping("/write.do")
    public ResponseEntity<String> write(@ModelAttribute QnaRequest qnaRequest, @CurrentUser Account account) {
        if (account == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }

        questionService.write(qnaRequest, account);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list.do")
    public List<QnaRequest> getMyQnaList(@CurrentUser Account account) {
        if (account == null) {
            return List.of();
        }
        return questionService.getMyQnaList(account);
    }

}
