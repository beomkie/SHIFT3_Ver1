package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.AdminContentFeedService;
import com.sch.shift3.user.dto.ContentFeedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        // create product
        adminContentFeedService.createContentFeed(contentFeedDto);

        return ResponseEntity.noContent().build();
    }

//    @Transactional
//    @PostMapping("/edit.do")
//    public ResponseEntity<String> editProduct(@ModelAttribute ContentFeedDto contentFeedDto){
//        log.info("contentFeedDto: {}", contentFeedDto);
//
//        // edit product
//        Product product = adminProductService.editProduct(productDto);
//        adminProductService.editProductImage(product, productDto.getImageList());
//
//        return ResponseEntity.noContent().build();
//    }
}
