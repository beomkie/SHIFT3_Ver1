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

    @Column(name = "answer", length = 1000)
    private String answer;

    @Column(name = "question_type", nullable = false, length = 20)
    private String questionType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @CreatedDate
    @Column(name = "question_at")
    private LocalDateTime questionAt;

    @Column(name = "answer_at")
    private LocalDateTime answerAt;

    public QnaRequest of(){
        return QnaRequest.builder()
                .id(id)
                .questionTitle(questionTitle)
                .questionDescription(questionDescription)
                .account(account)
                .answer(answer)
                .questionType(questionType)
                .questionAt(questionAt)
                .answerAt(answerAt)
                .build();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isSameAccount(Account account) {
        return this.account.getId().equals(account.getId());
    }

    public void updateQna(QnaRequest qnaRequest) {
        this.answer = qnaRequest.getAnswer();
        this.answerAt = LocalDateTime.now();
    }
}