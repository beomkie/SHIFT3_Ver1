package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.ContentFeed;
import com.sch.shift3.user.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link com.sch.shift3.user.entity.ContentFeed} entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentFeedDto implements Serializable {
    private Integer id;
    private String description;
    private String title;
    private String thumbnailFileName;
    private String category;
    private LocalDateTime createdAt;

    private Integer hit;

    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public ContentFeed toEntity(){
        return ContentFeed.builder()
                .id(id)
                .description(description)
                .hit(0)
                .title(title)
                .category(category)
                .thumbnailFileName(thumbnailFileName)
                .createdAt(createdAt)
                .build();
    }
}