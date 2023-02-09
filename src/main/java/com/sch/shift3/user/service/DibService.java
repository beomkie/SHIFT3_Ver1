package com.sch.shift3.user.service;

import com.sch.shift3.user.repository.DibRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DibService {
    private final DibRepository dibRepository;

    public boolean isProductDib(int accountId, int productId) {
        return dibRepository.isProductDib(accountId, productId);
    }

    public boolean isShopDib(int accountId, int shopId) {
        return dibRepository.isShopDib(accountId, shopId);
    }
}
