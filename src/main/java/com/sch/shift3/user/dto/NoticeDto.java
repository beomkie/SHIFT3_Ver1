package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Notice} entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    public Notice toEntity(){
        return Notice.builder()
                .id(id)
                .title(title)
                .description(description)
                .build();
    }
}