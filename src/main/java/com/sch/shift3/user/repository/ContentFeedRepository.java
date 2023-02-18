package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.ContentFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentFeedRepository extends JpaRepository<ContentFeed, Integer> {
    @Query(value = "SELECT *, match(title, description) against(?1) as score\n" +
            "FROM content_feed WHERE match(title, description) against(?1) AND id != ?2 ORDER BY score DESC LIMIT ?3", nativeQuery = true)
    List<ContentFeed> getResemblanceFeed(String title, int excludeFeedId, int limit);
}
