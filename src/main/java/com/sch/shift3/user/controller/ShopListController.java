package com.sch.shift3.user.controller;

import com.sch.shift3.user.dto.ShopRequest;
import com.sch.shift3.user.entity.SelectShop;
import com.sch.shift3.user.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop-list/search.do")
@RequiredArgsConstructor
public class ShopListController {
    private final ShopRepository shopRepository;

    @GetMapping("")
    public List<SelectShop> search(ShopRequest shopRequest) {
        return shopRepository.getShopList(shopRequest);
    }

}
