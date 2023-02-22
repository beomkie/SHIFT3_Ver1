package com.sch.shift3.user.controller;

import com.sch.shift3.config.CurrentUser;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.service.DibService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DibController {
    private final DibService dibService;

    @GetMapping("/dib/product/{productId}")
    public void dib(@PathVariable Integer productId, @CurrentUser Account account) {
        if (productId == null)
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");

        if (account == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");

        dibService.dibProduct(account.getId(), productId);
    }

    @GetMapping("/dib/product/remove/{productId}")
    public void removeDib(@PathVariable Integer productId, @CurrentUser Account account) {
        if (productId == null)
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");

        if (account == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");

        dibService.removeDibProduct(account.getId(), productId);
    }

    // pagination
    @GetMapping("/dib/product")
    public PageImpl<Product> getDibProductList(@CurrentUser Account account,
                                               Pageable pageRequest) {
        if (account == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");

        return dibService.getDibProductList(account.getId(), pageRequest);
    }



    @GetMapping("/dib/shop/{shopId}")
    public void shopDib(@PathVariable Integer shopId, @CurrentUser Account account) {
        if (shopId == null)
            throw new IllegalArgumentException("해당 샵이 존재하지 않습니다.");

        if (account == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");

        dibService.dibShop(account.getId(), shopId);
    }

    @GetMapping("/dib/shop/remove/{shopId}")
    public void shopUnDib(@PathVariable Integer shopId, @CurrentUser Account account) {
        if (shopId == null)
            throw new IllegalArgumentException("해당 샵이 존재하지 않습니다.");

        if (account == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");

        dibService.removeDibShop(account.getId(), shopId);
    }

    @GetMapping("/dib/shop/check/{shopId}")
    public ResponseEntity<String> checkShopDib(@PathVariable Integer shopId, @CurrentUser Account account) {
        if (shopId == null)
            throw new IllegalArgumentException("해당 샵이 존재하지 않습니다.");

        if (account == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");

        boolean isDib = dibService.isShopDib(account.getId(), shopId);
        // return isDib Json

        return ResponseEntity.ok(new JSONObject().put("isDib", isDib).toString());
    }
}
