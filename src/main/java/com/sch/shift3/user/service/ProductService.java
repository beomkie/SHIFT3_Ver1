package com.sch.shift3.user.service;

import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.repository.FeedRepository;
import com.sch.shift3.user.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductService {
    private final FeedRepository feedRepository;
    private final ProductRepository productRepository;

    public Product getProductById(int productId){
        // todo 이미지 순서 보장
        Product product = productRepository.findByIdOrderByImagesId(productId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return product;
    }

    public List<Product> getProductsByFeed(int feedId) {
        return feedRepository.getFeedProducts(feedId);
    }
}
