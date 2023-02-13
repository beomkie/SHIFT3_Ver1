package com.sch.shift3.user.service;

import com.sch.shift3.user.entity.Dib;
import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.repository.DibRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DibService {
    private final DibRepository dibRepository;
    private final ProductService productService;

    @Transactional
    public boolean isProductDib(int accountId, int productId) {
        return dibRepository.isProductDib(accountId, productId);
    }
    @Transactional
    public void dibProduct(int accountId, int productId) {
        dibRepository.dibProduct(accountId, productId);
    }

    @Transactional
    public boolean isShopDib(int accountId, int shopId) {
        return dibRepository.isShopDib(accountId, shopId);
    }

    @Transactional
    public void removeDibProduct(Integer accountId, Integer productId) {
        dibRepository.removeDibProduct(accountId, productId);
    }

    public PageImpl<Product> getDibProductList(Integer accountId, Pageable page) {
        PageImpl<Dib> dibs = dibRepository.getDibProductList(accountId, page);

        List<Integer> productIds = dibs.stream()
                .map(Dib::getProductId)
                .toList();

        PageImpl<Product> result = productService.getProductList(productIds, Pageable.unpaged());
        List<Product> content = new ArrayList<>();

        // dib order apply to product
        dibs.forEach(dib -> {
            result.getContent().stream()
                    .filter(product -> product.getId().equals(dib.getProductId()))
                    .findFirst()
                    .ifPresent(content::add);
        });

        // change page and size to dibs
        return new PageImpl<>(content, dibs.getPageable(), dibs.getTotalElements());
    }
}
