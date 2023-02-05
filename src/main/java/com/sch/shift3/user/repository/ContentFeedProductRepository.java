package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.ContentFeedProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentFeedProductRepository extends JpaRepository<ContentFeedProduct, Integer> {
    @EntityGraph(attributePaths = {"feed"})
    List<ContentFeedProduct> findAllByOrderByIdDesc();

    @EntityGraph(attributePaths = {"feed"})
    List<ContentFeedProduct> findTop3ByOrderByIdDesc();
//    @EntityGraph(attributePaths = {"feed", "products"})
//    Optional<ContentFeedProduct> findById(int id);

    @EntityGraph(attributePaths = {"feed"})
    List<ContentFeedProduct> findTop3ByFeedCategoryOrderByIdDesc(String category);
}
