package com.sch.shift3.user.entity;

import com.sch.shift3.user.dto.QnaRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "qna")
@EntityListeners(AuditingEntityListener.class)
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "question_title", nullable = false, length = 100)
    private String questionTitle;

    @Column(name = "question_description", nullable = false, length = 100)
    private String questionDescription;

    @Column(name = "anwser", length = 1000)
    private String anwser;

    @Column(name = "question_type", nullable = false, length = 10)
    private String questionType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @CreatedDate
    @Column(name = "question_at")
    private LocalDateTime questionAt;

    @Column(name = "anwser_at")
    private LocalDateTime anwserAt;

    public QnaRequest of(){
        return QnaRequest.builder()
                .id(id)
                .questionTitle(questionTitle)
                .questionDescription(questionDescription)
                .account(account)
                .anwser(anwser)
                .questionType(questionType)
                .questionAt(questionAt)
                .anwserAt(anwserAt)
                .build();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isSameAccount(Account account) {
        return this.account.getId().equals(account.getId());
    }
}