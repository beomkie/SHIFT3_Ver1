package com.sch.shift3.user.entity;

import com.sch.shift3.user.dto.ContentFeedDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "content_feed")
@EntityListeners(AuditingEntityListener.class)
public class ContentFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "category", nullable = false, length = 15)
    private String category;

    @Column(name = "thumbnail_file_name", nullable = false)
    private String thumbnailFileName;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public String getDescriptionNoHtml() {
        return Jsoup.parse(this.description).text();
    }

    public void update(ContentFeedDto contentFeedDto) {
        this.description = contentFeedDto.getDescription();
        this.title = contentFeedDto.getTitle();
        this.category = contentFeedDto.getCategory();
        this.thumbnailFileName = contentFeedDto.getThumbnailFileName();
    }
}