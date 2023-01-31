package com.sch.shift3.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "select_shop_brand")
public class SelectShopBrand {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(name = "select_shop_id", nullable = false)
//    private Integer selectShopId;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "select_shop_id", insertable = false, updatable = false)
//    private SelectShop ;

    @Column(name = "brand_name", nullable = false, length = 50)
    private String brandName;

    @ManyToOne
    @JoinColumn(name = "select_shop_id")
    private SelectShop selectShop;

    public void setSelectShop(SelectShop selectShop) {
        this.selectShop = selectShop;
    }

    public SelectShopBrand(String brandName, SelectShop selectShop) {
        this.brandName = brandName;
        this.selectShop = selectShop;
    }
}