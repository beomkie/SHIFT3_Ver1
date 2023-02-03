package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.AdminImageService;
import com.sch.shift3.admin.service.AdminShopService;
import com.sch.shift3.user.dto.SelectShopDto;
import com.sch.shift3.user.entity.SelectShop;
import com.sch.shift3.user.repository.ImageSelectShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/shop")
public class AdminShopController {

    private final AdminShopService adminShopService;
    private final AdminImageService adminImageService;
    private final ImageSelectShopRepository imageSelectShopRepository;


    @Transactional
    @PostMapping("/create.do")
    public ResponseEntity<String> createShop(@ModelAttribute SelectShopDto selectShopDto){
        log.info("selectShopDto: {}", selectShopDto);

        // create shop
        SelectShop selectShop = adminShopService.createShop(selectShopDto);
        adminShopService.editShopImage(selectShop, selectShopDto.getImageList());

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PostMapping("/edit.do")
    public ResponseEntity<String> editShop(@ModelAttribute SelectShopDto selectShopDto){
        log.info("selectShopDto: {}", selectShopDto);

        // edit shop
        SelectShop selectShop = adminShopService.editShop(selectShopDto);
        adminShopService.editShopImage(selectShop, selectShopDto.getImageList());
        adminShopService.editShopBrand(selectShop, selectShopDto.getBrand());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search.do")
    public List<SelectShop> findShop(@RequestParam String name){
        return adminShopService.findShopByName(name);
    }

}
