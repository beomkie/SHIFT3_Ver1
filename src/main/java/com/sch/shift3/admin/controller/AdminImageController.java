package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.AdminImageService;
import com.sch.shift3.user.entity.ImageProduct;
import com.sch.shift3.user.entity.ImageSelectShop;
import com.sch.shift3.user.repository.ImageRepository;
import com.sch.shift3.user.repository.ImageSelectShopRepository;
import com.sch.shift3.utill.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/image")
public class AdminImageController {
    private final AdminImageService adminImageService;
    private final ImageSelectShopRepository imageSelectShopRepository;
    private final ImageRepository imageRepository;

    @Transactional
    @PostMapping("/uploadShop")
    public ResponseEntity<String> uploadShopImage(@ModelAttribute MultipartFile file){
        File originalFile = ImageUtil.upload(file);
        String originalFileName = originalFile.getName();

        try {
            ImageUtil.uploadImageCompressed(originalFile);
        } catch (IOException e) {
            // remove original file
            ImageUtil.removeImage(originalFileName);
            return ResponseEntity.badRequest().body("썸네일 생성 오류");
        }
        ImageSelectShop imageSelectShop = ImageSelectShop.builder()
                .imageName(originalFileName)
                .build();

        imageRepository.save(imageSelectShop);

        // return image.id
        return ResponseEntity.ok("{\"id\":" + imageSelectShop.getId() +
                ", \"fileName\":\"" + originalFileName + "\"}"); // json
    }
    @PostMapping("/uploadFeed")
    public ResponseEntity<String> uploadFeedImage(@ModelAttribute MultipartFile file){
        File originalFile = ImageUtil.upload(file);
        String originalFileName = originalFile.getName();

        try {
            ImageUtil.uploadImageCompressed(originalFile);
        } catch (IOException e) {
            // remove original file
            ImageUtil.removeImage(originalFile.getName());
            return ResponseEntity.badRequest().body("썸네일 생성 오류");
        }

        return ResponseEntity.ok("{\"fileName\":\"" + originalFileName + "\"}"); // json
    }

    @Transactional
    @PostMapping("/uploadProduct")
    public ResponseEntity<String> uploadProductImage(@ModelAttribute MultipartFile file){

        String fileName = ImageUtil.upload(file).getName();
        ImageProduct productImage = ImageProduct.builder()
                .imageName(fileName)
                .build();

        imageRepository.save(productImage);

        // return image.id
        return ResponseEntity.ok("{\"id\":" + productImage.getId() +
                ", \"fileName\":\"" + fileName + "\"}"); // json
    }

    @Transactional
    @GetMapping("/remove/{id}")
    public ResponseEntity<String> removeImage(@PathVariable Integer id){
        imageRepository.deleteById(id);
        return ResponseEntity.ok("success");
    }
}
