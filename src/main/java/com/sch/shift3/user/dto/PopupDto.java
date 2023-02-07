package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.Popup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Popup} entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopupDto implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private Integer width;
    private Integer height;

    private LocalDateTime createdAt;

    public String getDescriptionNoHtml(){
        return Jsoup.parse(this.description).text();
    }

    public Popup toEntity() {
        return Popup.builder()
                .id(id)
                .title(title)
                .description(description)
                .width(width)
                .height(height)
                .build();
    }
}