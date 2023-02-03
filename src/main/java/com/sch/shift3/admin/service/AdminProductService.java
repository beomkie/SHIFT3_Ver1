package com.sch.shift3.admin.service;

import com.sch.shift3.user.dto.ProductDto;
import com.sch.shift3.user.entity.ImageProduct;
import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.entity.SelectShop;
import com.sch.shift3.user.repository.ImageProductRepository;
import com.sch.shift3.user.repository.ProductRepository;
import com.sch.shift3.user.repository.SelectShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProductService {
    private final ProductRepository productRepository;
    private final SelectShopRepository selectShopRepository;
    private final ImageProductRepository imageProductRepository;
    private final AdminImageService adminImageService;

    public List<ProductDto> getAllProductList(){
        List<ProductDto> productList = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            ProductDto productDto = product.of();
            productList.add(productDto);
        });

        return productList;
    }

    public ProductDto getProduct(Integer shopId){
        Product product = productRepository.findByIdOrderByIdAsc(shopId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return product.of();
    }

    @Transactional
    public Product createProduct(ProductDto productDto){
        if (productDto.getShopId() == null) {
            throw new IllegalArgumentException("상품을 등록할 편집샵을 선택해주세요.");
        }
        SelectShop selectShop = selectShopRepository.findById(productDto.getShopId()).orElseThrow(() -> new IllegalArgumentException("해당 편집샵이 존재하지 않습니다."));
        Product product = productDto.toEntity();
        product.setSelectShop(selectShop);

        return productRepository.save(product);
    }

    @Transactional
    public Product editProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        product.update(productDto);

        return productRepository.save(product);
    }

    @Transactional
    public void editProductImage(Product product, List<ImageProduct> productImages) {
        for (ImageProduct image : productImages) {
            imageProductRepository.findById(image.getId()).ifPresent(productImage -> {
                productImage.setProduct(product);
                imageProductRepository.save(productImage);
            });
        }
    }
}
