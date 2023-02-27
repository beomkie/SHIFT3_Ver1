package com.sch.shift3.user.service;

import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.repository.FeedRepository;
import com.sch.shift3.user.repository.ProductRepository;
import com.sch.shift3.user.repository.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductService {
    private final FeedRepository feedRepository;
    private final ProductRepository productRepository;
    private final ProductRepositoryCustom productRepositoryCustom;

    public Product getProductById(int productId){
        Product product = productRepositoryCustom.findProductById(productId);
        return product;
    }

    public List<Product> getProductsByFeed(int feedId) {
        return feedRepository.getFeedProducts(feedId);
    }

    public PageImpl<Product> getProductList(List<Integer> productIds, Pageable pageable) {
        return productRepositoryCustom.getProductList(productIds, pageable);
    }
    public List<Product> getRelatedProduct(Product product) {
        return productRepository.getResemblanceProduct(product.getDescription(), product.getId(), 4);
    }
}
