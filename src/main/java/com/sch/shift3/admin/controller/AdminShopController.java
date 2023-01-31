package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.AdminImageService;
import com.sch.shift3.admin.service.AdminShopService;
import com.sch.shift3.user.dto.SelectShopDto;
import com.sch.shift3.utill.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/shop")
public class AdminShopController {

    private final AdminShopService adminShopService;
    private final AdminImageService adminImageService;


    @Transactional
    @PostMapping("/create.do")
    public ResponseEntity<String> createShop(@ModelAttribute SelectShopDto selectShopDto){
        log.info("selectShopDto: {}", selectShopDto);

        // create shop
        Integer shopId = adminShopService.createShop(selectShopDto);

        // upload shop Image on storage
        if (selectShopDto.getImages() != null)
            selectShopDto.getImages().forEach(image -> {
                if (image.isEmpty()) return;

                String fileName = ImageUtil.upload(image);
                // upload shop Image on DB
                adminImageService.uploadShopImageOnDB(fileName, shopId);
            });

        return ResponseEntity.noContent().build();
    }

}
