package com.sch.shift3.user.service;

import com.sch.shift3.user.entity.ContentFeedProduct;
import com.sch.shift3.user.repository.ContentFeedProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeedService {
    private final ContentFeedProductRepository contentFeedProductRepository;

    public List<ContentFeedProduct> getRecentFeed(){
        return contentFeedProductRepository.findTop3ByOrderByIdDesc();
    }

    public List<ContentFeedProduct> getFeedByCategory(String category){
        List<ContentFeedProduct> feedByCategory = contentFeedProductRepository.findTop3ByFeedCategoryOrderByIdDesc(category);
        feedByCategory.forEach(feed -> {
            // remove description's html tag
            // and 20 length
            String description = feed.getFeed().getDescriptionNoHtml();
            if(description.length() > 50){
                description = description.substring(0, 20) + "...";
            }
            feed.getFeed().setDescription(description);
        });
        return contentFeedProductRepository.findTop3ByFeedCategoryOrderByIdDesc(category);
    }

}
