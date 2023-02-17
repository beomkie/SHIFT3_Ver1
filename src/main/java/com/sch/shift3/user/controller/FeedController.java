package com.sch.shift3.user.controller;

import com.sch.shift3.user.dto.ContentFeedDto;
import com.sch.shift3.user.entity.ContentFeed;
import com.sch.shift3.user.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    @GetMapping("/related.do")
    public List<ContentFeedDto> releatedFeedController(@RequestParam Integer shopId) {
        if (shopId == null) {
            throw new IllegalArgumentException("shopId is null");
        }
        return feedService.getRelatedFeedByShopId(shopId).stream().map(ContentFeed::of).toList();
    }

    @GetMapping("/search.do")
    public PageImpl<ContentFeed> searchFeedController(@RequestParam String keyword,
                                                      @RequestParam(defaultValue = "전체") String category,
                                                      Pageable pageable) {
        if (category == null) {
            throw new IllegalArgumentException("category is null");
        }

        return feedService.searchFeed(keyword, category, pageable);
    }

}
