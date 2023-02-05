package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.SelectShop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectShopRepository extends JpaRepository<SelectShop, Integer> {
    @EntityGraph(attributePaths = {"imageSelectShops", "selectShopBrands"})
    List<SelectShop> findAll();
    List<SelectShop> findByNameContaining(String name);
//    @EntityGraph(attributePaths = {"selectShopBrandList", "imageSelectShopList"})
}
