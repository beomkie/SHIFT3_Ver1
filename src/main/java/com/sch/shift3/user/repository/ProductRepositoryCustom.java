package com.sch.shift3.user.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final ProductRepository productRepository;

    public Product findProductById(int productId) {
        QProduct product = QProduct.product;

        return queryFactory
                .selectFrom(product)
                .where(product.id.eq(productId))
                .leftJoin(product.images).fetchJoin()
                .fetchOne();
    }

    public List<Product> findAll(){
        QProduct product = QProduct.product;

        return queryFactory
                .selectFrom(product)
                .leftJoin(product.images).fetchJoin()
                .fetch();
    }

    public PageImpl<Product> getProductList(List<Integer> productIds, Pageable pageable) {
        QProduct product = QProduct.product;

        JPAQuery<Product> query = queryFactory
                .selectFrom(product)
                .where(product.id.in(productIds));

        if (pageable.isPaged()) {
            query.offset(pageable.getOffset());
            query.limit(pageable.getPageSize());
        }

        List<Product> products = query.leftJoin(product.images).fetchJoin().fetch();

        return new PageImpl<>(products, pageable, products.size());
    }

    public List<Product> findProductsByNameContainingNoImage(String name) {
        QProduct product = QProduct.product;

        return queryFactory
                .selectFrom(product)
                .where(product.name.contains(name))
                .fetch();
    }

    public List<Product> findProductsBySelectShopNameNoImage(String name) {
        QProduct product = QProduct.product;

        return queryFactory
                .selectFrom(product)
                .where(product.selectShop.name.eq(name))
                .fetch();
    }

    public List<Product> getProductsByShopId(Integer shopId){
        QProduct product = QProduct.product;
        JPAQuery<Product> query = queryFactory.select(product).from(product)
                .where(product.selectShop.id.eq(shopId));
        return query.fetch();

    }
}
