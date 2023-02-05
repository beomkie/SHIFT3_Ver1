package com.sch.shift3.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sch.shift3.user.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "url", nullable = false, length = 300)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "select_shop_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SelectShop selectShop;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Builder.Default
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ImageProduct> images = new HashSet<>();

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    public ProductDto of() {
        return ProductDto.builder()
                .id(id)
                .name(name)
                .price(price)
                .url(url)
                .description(description)
                .selectShop(selectShop)
                .imageList(images.stream().toList())
                .createdAt(createdAt)
                .build();
    }

    public void update(ProductDto productDto) {
        this.name = productDto.getName();
        this.price = productDto.getPrice();
        this.url = productDto.getUrl();
        this.description = productDto.getDescription();
    }
}