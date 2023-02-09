package com.sch.shift3.admin.controller;

import com.sch.shift3.user.dto.QnaRequest;
import com.sch.shift3.user.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/cs")
public class AdminCsController {
    private final QuestionService questionService;

    @PostMapping("/update.do")
    public ResponseEntity<String> updateCs(@ModelAttribute QnaRequest qnaRequest){
        if (qnaRequest.getAnswer().isBlank())
            throw new IllegalArgumentException("답변을 입력해주세요.");

        questionService.updateQna(qnaRequest);
        return ResponseEntity.noContent().build();
    }

}
