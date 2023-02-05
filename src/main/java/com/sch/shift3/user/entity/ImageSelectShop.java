package com.sch.shift3.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("S")
@Entity(name = "image_select_shop")
public class ImageSelectShop extends Image{

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "select_shop_id")
    private SelectShop selectShop;

    public void setSelectShop(SelectShop selectShops) {
        this.selectShop = selectShops;
    }

    public ImageSelectShop(String imageName) {
        super(imageName);
    }
}