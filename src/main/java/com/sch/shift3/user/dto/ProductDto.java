package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.ImageProduct;
import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.entity.SelectShop;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link SelectShop} entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private Integer price;
    private String url;

    @Lob
    private String description;
    private Instant createdAt;

    @Builder.Default
    private List<ImageProduct> imageList = new ArrayList<>();

    private SelectShop selectShop;

    private Integer shopId;

    public Product toEntity(){
        return Product.builder()
            .id(id)
            .name(name)
            .price(price)
            .url(url)
            .description(description)
            .build();
    }
}