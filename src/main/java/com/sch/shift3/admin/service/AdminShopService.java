package com.sch.shift3.admin.service;

import com.sch.shift3.user.dto.SelectShopDto;
import com.sch.shift3.user.entity.ImageSelectShop;
import com.sch.shift3.user.entity.SelectShop;
import com.sch.shift3.user.entity.SelectShopBrand;
import com.sch.shift3.user.repository.ImageSelectShopRepository;
import com.sch.shift3.user.repository.SelectShopBrandRepository;
import com.sch.shift3.user.repository.SelectShopRepository;
import com.sch.shift3.utill.Address;
import com.sch.shift3.utill.Geocoding;
import com.sch.shift3.utill.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminShopService {
    private final SelectShopRepository selectShopRepository;
    private final SelectShopBrandRepository selectShopBrandRepository;
    private final ImageSelectShopRepository imageSelectShopRepository;
    private final SelectShopBrandRepository shopBrandRepository;
    private final AdminImageService adminImageService;
    private final Geocoding geocoding;

    public List<SelectShopDto> getAllShopList(){
        List<SelectShopDto> selectShopList = new ArrayList<>();
        selectShopRepository.findAll().forEach(selectShop -> {
            // get Shop images
//            List<String> imageList = new ArrayList<>();
//            imageSelectShopRepository.findAllBySelectShopId(selectShop.getId()).forEach(imageSelectShop -> {
//                imageList.add(imageSelectShop.getImageName());
//            });
            SelectShopDto selectShopDto = selectShop.of();
//            log.info("selectShopDto: {}", );
//            selectShopDto.setImageUrl(imageList);

//             get Brand List
            selectShopList.add(selectShopDto);
        });

        return selectShopList;
    }

    public SelectShopDto getShop(Integer shopId){
        SelectShop selectShop = selectShopRepository.findById(shopId).orElseThrow(() -> new IllegalArgumentException("해당 매장이 존재하지 않습니다."));

        // get Shop images
        List<String> imageList = new ArrayList<>();
        imageSelectShopRepository.findAllBySelectShopId(selectShop.getId()).forEach(imageSelectShop -> {
            imageList.add(imageSelectShop.getImageName());
        });
        SelectShopDto selectShopDto = selectShop.of();
        selectShopDto.setImageUrl(imageList);

        // get Brand List
        List<String> brandList = new ArrayList<>();
        shopBrandRepository.findAllBySelectShopId(selectShop.getId()).forEach(selectShopBrand -> {
            log.info("brandName : {}", selectShopBrand.getBrandName());
            brandList.add(selectShopBrand.getBrandName());
        });

        selectShopDto.setBrand(brandList);
        return selectShopDto;
    }

    @Transactional
    public Integer createShop(SelectShopDto selectShopDto){

        Address address = geocoding.getLatLng(selectShopDto.getStreetAddress());
        selectShopDto.setLatitude(address.getX());
        selectShopDto.setLongitude(address.getY());

        SelectShop selectShop = selectShopDto.toEntity();

        // upload shop Image on storage
        if (selectShopDto.getImages() != null)
            selectShopDto.getImages().forEach(image -> {
                if (image.isEmpty()) return;

                String fileName = ImageUtil.upload(image);
                // upload shop Image on DB
                selectShop.addImageSelectShop(
                        ImageSelectShop.builder()
                                .imageName(fileName)
                                .selectShop(selectShop)
                                .build()
                );
            });


        // brand 저장
        if (selectShopDto.getBrand() != null)
            selectShopDto.getBrand().forEach(brand -> {
                if (brand.isBlank()) return;

                selectShop.addSelectShopBrand(
                        SelectShopBrand.builder()
                                .brandName(brand)
                                .selectShop(selectShop)
                                .build()
                );
            });

        selectShopRepository.save(selectShop);

        return selectShop.getId();
    }
}
