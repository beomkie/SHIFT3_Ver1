package com.sch.shift3.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("S")
@Entity(name = "image_select_shop")
public class ImageSelectShop extends Image{

    @Column(name = "select_shop_id")
    private Integer selectShopId;

    public ImageSelectShop(String path, Integer selectShopId) {
        super(null, path);
        this.selectShopId = selectShopId;
    }
}