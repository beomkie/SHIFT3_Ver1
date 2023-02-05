package com.sch.shift3.user.entity;

import com.sch.shift3.user.dto.ContentFeedDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "content_feed_product")
public class ContentFeedProduct {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private ContentFeed feed;

    @Column(name = "product_id")
    private Integer productId;



    public ContentFeedDto of() {
        return ContentFeedDto.builder()
                .id(feed.getId())
                .description(feed.getDescription())
                .title(feed.getTitle())
                .category(feed.getCategory())
                .thumbnailFileName(feed.getThumbnailFileName())
                .build();
    }
}