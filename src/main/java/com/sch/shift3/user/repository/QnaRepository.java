package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
    List<Qna> findAllByAccountId(Integer accountId);
}
