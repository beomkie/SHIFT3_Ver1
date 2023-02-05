package com.sch.shift3.admin.service;

import com.sch.shift3.user.dto.ContentFeedDto;
import com.sch.shift3.user.entity.ContentFeed;
import com.sch.shift3.user.entity.ContentFeedProduct;
import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.repository.ContentFeedProductRepository;
import com.sch.shift3.user.repository.ContentFeedRepository;
import com.sch.shift3.user.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminContentFeedService {

    private final ContentFeedRepository contentFeedRepository;
    private final ContentFeedProductRepository contentFeedProductRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createContentFeed(ContentFeedDto contentFeedDto) {
        ContentFeed contentFeed = contentFeedDto.toEntity();
        Set<ContentFeedProduct> connectedProducts = new HashSet<>();

        // product connect
        contentFeedDto.getProducts()
                        .stream()
                        .filter(item -> item.getId() != null)
                        .forEach(
                            item -> {
                                Product product = productRepository.findById(item.getId()).orElseThrow(() -> new IllegalArgumentException("product not found"));
                                connectedProducts.add(new ContentFeedProduct(null, contentFeed, item.getId()));
                            });

        contentFeedProductRepository.saveAll(connectedProducts);
    }

    public List<ContentFeedDto> getAllContentList() {
        return contentFeedProductRepository.findAllByOrderByIdDesc().stream()
                .map(ContentFeedProduct::of).toList();
    }
}
