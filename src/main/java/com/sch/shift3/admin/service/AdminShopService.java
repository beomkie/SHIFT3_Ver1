package com.sch.shift3.admin.service;

import com.sch.shift3.user.dto.SelectShopDto;
import com.sch.shift3.user.entity.SelectShop;
import com.sch.shift3.user.entity.SelectShopBrand;
import com.sch.shift3.user.repository.SelectShopBrandRepository;
import com.sch.shift3.user.repository.SelectShopRepository;
import com.sch.shift3.utill.Address;
import com.sch.shift3.utill.Geocoding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminShopService {
    private final SelectShopRepository selectShopRepository;
    private final SelectShopBrandRepository selectShopBrandRepository;
    private final Geocoding geocoding;

    @Transactional
    public Integer createShop(SelectShopDto selectShopDto){

        Address address = geocoding.getLatLng(selectShopDto.getStreetAddress());
        selectShopDto.setLatitude(address.getX());
        selectShopDto.setLongitude(address.getY());

        SelectShop selectShop = selectShopDto.toEntity();
        selectShopRepository.save(selectShop);

        // brand 저장
        List<SelectShopBrand> selectShopBrandList = new ArrayList<>();

        if (selectShopDto.getBrand() != null)
            for (String brand : selectShopDto.getBrand()) {
                SelectShopBrand selectShopBrand = SelectShopBrand.builder()
                        .selectShopId(selectShop.getId())
                        .brandName(brand)
                        .build();
                selectShopBrandList.add(selectShopBrand);
            }

        if (!selectShopBrandList.isEmpty())
            selectShopBrandRepository.saveAll(selectShopBrandList);

        return selectShop.getId();
    }
}
