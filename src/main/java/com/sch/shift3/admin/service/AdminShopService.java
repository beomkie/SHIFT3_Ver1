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
            SelectShopDto selectShopDto = selectShop.of();
            selectShopList.add(selectShopDto);
        });

        return selectShopList;
    }

    public SelectShopDto getShop(Integer shopId){
        SelectShop selectShop = selectShopRepository.findById(shopId).orElseThrow(() -> new IllegalArgumentException("해당 편집샵이 존재하지 않습니다."));
        return selectShop.of();
    }

    @Transactional
    public SelectShop createShop(SelectShopDto selectShopDto){

        Address address = geocoding.getLatLng(selectShopDto.getStreetAddress());
        selectShopDto.setLatitude(address.getX());
        selectShopDto.setLongitude(address.getY());

        SelectShop selectShop = selectShopDto.toEntity();

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

        return selectShopRepository.save(selectShop);
    }

    @Transactional
    public SelectShop editShop(SelectShopDto selectShopDto) {
        SelectShop selectShop = selectShopRepository.findById(selectShopDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 편집샵이 존재하지 않습니다."));

        Address address = geocoding.getLatLng(selectShopDto.getStreetAddress());
        selectShopDto.setLatitude(address.getX());
        selectShopDto.setLongitude(address.getY());

        selectShop.update(selectShopDto);

        return selectShopRepository.save(selectShop);
    }

    @Transactional
    public void editShopImage(SelectShop shop, List<ImageSelectShop> imageSelectShops) {
        for (ImageSelectShop image : imageSelectShops) {
            imageSelectShopRepository.findById(image.getId()).ifPresent(imageSelectShop -> {
                imageSelectShop.setSelectShop(shop);
                imageSelectShopRepository.save(imageSelectShop);
            });
        }
    }

    @Transactional
    public void editShopBrand(SelectShop shop, List<String> shopBrands) {
        // 기존 브랜드 삭제
        shopBrandRepository.deleteAllBySelectShop(shop);

        List<SelectShopBrand> selectShopBrands = new ArrayList<>();
        for (String shopBrand : shopBrands) {
            if (shopBrand == null) continue;

            selectShopBrands.add(
                    SelectShopBrand.builder()
                            .brandName(shopBrand)
                            .selectShop(shop)
                            .build()
            );
        }
        selectShopBrandRepository.saveAll(selectShopBrands);
    }


    @Transactional
    public List<SelectShop> findShopByName(String name) {
        return selectShopRepository.findByNameContaining(name);
    }
}
