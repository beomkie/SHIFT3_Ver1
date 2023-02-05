package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @EntityGraph(attributePaths = {"images"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"images"})
    Optional<Product> findByIdOrderByIdAsc(Integer id);

    @EntityGraph(attributePaths = {"images"})
    List<Product> findByNameContaining(String name);

    @EntityGraph(attributePaths = {"images"})
    List<Product> findBySelectShopName(String selectShopName);

}
