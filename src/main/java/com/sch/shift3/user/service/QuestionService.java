package com.sch.shift3.user.service;

import com.sch.shift3.user.dto.QnaRequest;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.entity.Qna;
import com.sch.shift3.user.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QnaRepository qnaRepository;
    public void write(QnaRequest qnaRequest, Account account) {
        Qna qna = qnaRequest.toEntity();
        qna.setAccount(account);
        qnaRepository.save(qna);
    }

    public List<QnaRequest> getMyQnaList(Account account) {
        if (account == null)
            return List.of();

        return qnaRepository.findAllByAccountIdOrderByIdDesc(account.getId())
                .stream().map(Qna::of).toList();
    }

    public QnaRequest getMyQnaById(Account account, Integer qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 문의가 존재하지 않습니다."));

        if (!qna.isSameAccount(account)) {
            throw new IllegalArgumentException("해당 문의가 존재하지 않습니다.");
        }

        return qna.of();
    }

    public List<QnaRequest> getAllQuestionList() {
        return qnaRepository.findAllByOrderByIdDesc().stream().map(Qna::of).toList();
    }

    public void updateQna(QnaRequest qnaRequest) {
        Qna qna = qnaRepository.findById(qnaRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 문의가 존재하지 않습니다."));

        qna.updateQna(qnaRequest);
        qnaRepository.save(qna);
    }
}
