package com.sch.shift3.user.service;

import com.sch.shift3.user.entity.SelectShop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShopService {

    public List<SelectShop> getSelectShopList(double latitude, double longitude, String filter){
        // filter pop, new, distance(??)
        return null;
    }

}
