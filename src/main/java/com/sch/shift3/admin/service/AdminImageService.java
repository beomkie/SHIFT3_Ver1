package com.sch.shift3.admin.service;

import com.sch.shift3.user.entity.ImageSelectShop;
import com.sch.shift3.user.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminImageService {

    private final ImageRepository imageRepository;

    public void uploadShopImageOnDB(String fileName, Integer shopId){
        ImageSelectShop imageSelectShop = new ImageSelectShop(fileName, shopId);
        imageRepository.save(imageSelectShop);
    }
}
