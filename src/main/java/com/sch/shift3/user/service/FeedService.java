package com.sch.shift3.user.service;

import com.sch.shift3.user.entity.ContentFeed;
import com.sch.shift3.user.entity.ContentFeedProduct;
import com.sch.shift3.user.repository.ContentFeedProductRepository;
import com.sch.shift3.user.repository.ContentFeedRepository;
import com.sch.shift3.user.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final ContentFeedProductRepository contentFeedProductRepository;
    private final ContentFeedRepository contentFeedRepository;
    private final FeedRepository feedRepository;

    public List<ContentFeed> getRecentFeed(){
        return feedRepository.getRecentFeed(3);
    }

    public List<ContentFeedProduct> getFeedByCategory(String category){
        List<ContentFeedProduct> feedByCategory = feedRepository.findTop3ByFeedCategory(category);
        feedByCategory.forEach(feed -> {
            String description = feed.getFeed().getDescriptionNoHtml();
            if(description.length() > 50){
                description = description.substring(0, 20) + "...";
            }
            feed.getFeed().setDescription(description);
        });
        return feedByCategory;
    }

    public ContentFeed getFeedById(int id){
        return feedRepository.getFeedDetail(id);
    }

    public List<ContentFeed> getRelatedFeed(Integer productId) {
        return feedRepository.getRelatedFeed(productId);
    }
}
