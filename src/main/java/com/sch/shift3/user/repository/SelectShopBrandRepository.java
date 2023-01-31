package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.SelectShopBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectShopBrandRepository extends JpaRepository<SelectShopBrand, Integer> {
    List<SelectShopBrand> findAllBySelectShopId(Integer selectShopId);
}
