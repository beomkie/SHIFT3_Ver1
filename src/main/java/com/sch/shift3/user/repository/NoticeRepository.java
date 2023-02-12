package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    List<Notice> findAllByOrderByCreatedAtDesc();
}
