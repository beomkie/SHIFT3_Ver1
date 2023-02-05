package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.AdminProductService;
import com.sch.shift3.user.dto.ProductDto;
import com.sch.shift3.user.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {
    private final AdminProductService adminProductService;

    @Transactional
    @PostMapping("/create.do")
    public ResponseEntity<String> createProduct(@ModelAttribute ProductDto productDto){
        log.info("productDto: {}", productDto);

        // create product
        Product product = adminProductService.createProduct(productDto);
        adminProductService.editProductImage(product, productDto.getImageList());

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PostMapping("/edit.do")
    public ResponseEntity<String> editProduct(@ModelAttribute ProductDto productDto){
        log.info("productDto: {}", productDto);

        // edit product
        Product product = adminProductService.editProduct(productDto);
        adminProductService.editProductImage(product, productDto.getImageList());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search.do")
    public List<Product> searchProduct(@RequestParam String name){
        return adminProductService.findProductByName(name);
    }
}
