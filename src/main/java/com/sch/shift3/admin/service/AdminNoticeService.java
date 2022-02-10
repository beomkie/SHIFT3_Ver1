package com.sch.shift3.admin.service;

import com.sch.shift3.user.dto.NoticeDto;
import com.sch.shift3.user.entity.Notice;
import com.sch.shift3.user.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {
    private final NoticeRepository noticeRepository;

    public List<NoticeDto> getNotices(){
        return noticeRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(Notice::of)
                .toList();
    }


    public Notice findNoticeById(int i) {
        return noticeRepository.findById(i).orElse(null);
    }
}
