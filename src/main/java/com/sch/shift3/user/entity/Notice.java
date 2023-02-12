package com.sch.shift3.user.entity;

import com.sch.shift3.user.dto.NoticeDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice")
@EntityListeners(AuditingEntityListener.class)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", length = 70)
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public NoticeDto of(){
        return NoticeDto.builder()
                .id(id)
                .title(title)
                .description(description)
                .createdAt(createdAt)
                .build();
    }
}