package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.ImageSelectShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageSelectShopRepository extends JpaRepository<ImageSelectShop, Integer> {
    List<ImageSelectShop> findAllBySelectShopId(Integer selectShopId);
}
