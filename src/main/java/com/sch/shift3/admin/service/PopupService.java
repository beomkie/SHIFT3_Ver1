package com.sch.shift3.admin.service;

import com.sch.shift3.user.dto.PopupDto;
import com.sch.shift3.user.entity.Popup;
import com.sch.shift3.user.repository.PopupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupService {
    private final PopupRepository popupRepository;

    @Transactional
    public void createPopup(PopupDto popupDto) {
        popupRepository.save(popupDto.toEntity());
    }

    @Transactional
    public void editPopup(PopupDto popupDto) {
        popupRepository.save(popupDto.toEntity());
    }

    public void deletePopup(Integer id) {
        popupRepository.deleteById(id);
    }

    public List<PopupDto> getAllPopupList() {
        return popupRepository.findAll().stream().map(Popup::of).toList();
    }

    public PopupDto getPopup(Integer id) {
        return popupRepository.findById(id).map(Popup::of).orElse(null);
    }
}
