package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @EntityGraph(attributePaths = {"images"})
    Optional<Product> findByIdOrderByIdAsc(Integer id);
    @EntityGraph(attributePaths = {"images"})
    List<Product> findByNameContaining(String name);

    @EntityGraph(attributePaths = {"images"})
    List<Product> findBySelectShopName(String selectShopName);

    @Query(value = "SELECT *, match(name, description) against(?1) as score\n" +
            "FROM product WHERE match(name, description) against(?1) AND id != ?2 ORDER BY score DESC LIMIT ?3", nativeQuery = true)
    List<Product> getResemblanceProduct(String description, Integer excludeFeedId, int limit);
}
