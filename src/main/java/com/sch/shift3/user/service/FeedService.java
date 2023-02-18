package com.sch.shift3.user.service;

import com.sch.shift3.user.entity.ContentFeed;
import com.sch.shift3.user.repository.ContentFeedProductRepository;
import com.sch.shift3.user.repository.ContentFeedRepository;
import com.sch.shift3.user.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public List<ContentFeed> getFeedByCategory(String category){
        List<ContentFeed> feedByCategory = feedRepository.findTop3ByFeedCategory(category);
        feedByCategory.forEach(feed -> {
            String description = feed.getDescriptionNoHtml();
            if(description.length() > 50){
                description = description.substring(0, 20) + "...";
            }
            feed.setDescription(description);
        });
        return feedByCategory;
    }

    @Transactional
    public ContentFeed getFeedById(int id){
        return feedRepository.getFeedDetail(id);
    }

    public List<ContentFeed> getRelatedFeed(Integer productId) {
        return feedRepository.getRelatedFeed(productId);
    }

    public List<ContentFeed> getRelatedFeedByShopId(Integer shopId) {
        List<ContentFeed> feeds = feedRepository.getRelatedFeedByShopId(shopId);
        feeds.forEach(feed -> {
            String description = feed.getDescriptionNoHtml();
            if(description.length() > 15){
                description = description.substring(0, 15) + "...";
            }
            feed.setDescription(description);
        });

        return feeds;
    }

    public PageImpl<ContentFeed> searchFeed(String keyword, String category, Pageable pageable) {
        PageImpl<ContentFeed> feeds = feedRepository.searchFeed(keyword, category, pageable);
        feeds.getContent().forEach(feed -> {
            String description = feed.getDescriptionNoHtml();
            if(description.length() > 50){
                description = description.substring(0, 20) + "...";
            }
            feed.setDescription(description);
        });

        return feeds;
    }

    public List<ContentFeed> getResemblanceFeed(ContentFeed feed, int limit) {
        var feeds = contentFeedRepository.getResemblanceFeed(feed.getTitle(), feed.getId(), limit);
        feeds.forEach(feedItem -> {
            String description = feedItem.getDescriptionNoHtml();
            if(description.length() > 50){
                description = description.substring(0, 20) + "...";
            }
            feedItem.setDescription(description);
        });
        return feeds;
    }
}
