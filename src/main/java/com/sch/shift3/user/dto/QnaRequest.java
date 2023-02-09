package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.entity.Qna;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Qna} entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaRequest implements Serializable {
    private Integer id;
    private String questionTitle;
    private String questionDescription;
    private String anwser;
    private String questionType;
    private LocalDateTime questionAt;
    private LocalDateTime anwserAt;

    private Account account;

    public Qna toEntity() {
        return Qna.builder()
                .id(id)
                .questionTitle(questionTitle)
                .questionDescription(questionDescription)
                .anwser("")
                .questionType(questionType)
                .anwserAt(anwserAt)
                .build();
    }


}