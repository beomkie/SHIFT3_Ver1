package com.sch.shift3.user.service;

import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductService {
    private final FeedRepository feedRepository;

    public Product getProductById(Long id){
        return null;
    }

    public List<Product> getProductsByFeed(int feedId) {
        return feedRepository.getFeedProducts(feedId);
    }
}
