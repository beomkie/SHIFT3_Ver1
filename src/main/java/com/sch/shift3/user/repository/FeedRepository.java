package com.sch.shift3.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sch.shift3.user.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FeedRepository{
    private final JPAQueryFactory queryFactory;
    private final ContentFeedRepository contentFeedRepository;
    private final ProductRepositoryCustom productRepositoryCustom;


    public ContentFeed getFeedDetail(int id){
        return queryFactory
                .selectFrom(QContentFeed.contentFeed)
                .where(QContentFeed.contentFeed.id.eq(id))
                .fetchOne();
    }

    public List<Product> getFeedProducts(int id){
        QContentFeedProduct qContentFeedProduct = QContentFeedProduct.contentFeedProduct;
        QProduct qProduct = QProduct.product;

        return queryFactory
                .select(qProduct)
                .from(qContentFeedProduct)
                .join(qProduct).on(qContentFeedProduct.productId.eq(qProduct.id))
                .where(qContentFeedProduct.feed.id.eq(id))
                .fetch();
    }

    public List<ContentFeed> getFeedList(){
        return queryFactory
                .selectFrom(QContentFeed.contentFeed)
                .orderBy(QContentFeed.contentFeed.id.asc())
                .fetch();
    }


    public List<ContentFeed> getRecentFeed(int limit) {
        return queryFactory
                .selectFrom(QContentFeed.contentFeed)
                .orderBy(QContentFeed.contentFeed.id.desc())
                .limit(limit)
                .fetch();

    }

    public List<ContentFeed> findTop3ByFeedCategory(String category) {
        QContentFeed feed = QContentFeed.contentFeed;
        return queryFactory
                .selectFrom(feed)
                .where(feed.category.eq(category))
                .orderBy(feed.id.desc())
                .limit(3)
                .fetch();
    }

    public List<ContentFeed> getRelatedFeed(Integer productId) {
        return queryFactory
                .select(QContentFeedProduct.contentFeedProduct.feed)
                .from(QContentFeedProduct.contentFeedProduct)
                .where(QContentFeedProduct.contentFeedProduct.productId.eq(productId))
                .limit(2)
                .orderBy(QContentFeedProduct.contentFeedProduct.id.desc())
                .fetch();
    }

    public List<ContentFeed> getRelatedFeedByShopId(Integer shopId){
        QContentFeedProduct qContentFeedProduct = QContentFeedProduct.contentFeedProduct;
        QContentFeed qContentFeed = QContentFeed.contentFeed;

        List<Product> products = productRepositoryCustom.getProductsByShopId(shopId);
        List<ContentFeedProduct> list = queryFactory
                .select(qContentFeedProduct)
                .from(qContentFeedProduct)
                .where(
                        qContentFeedProduct.productId.in(products.stream().map(Product::getId).toList())
                )
                .orderBy(QContentFeedProduct.contentFeedProduct.id.desc())
                .fetch();

        return queryFactory
                .select(qContentFeed)
                .from(qContentFeed)
                .where(
                        qContentFeed.id.in(list.stream().map(
                                item -> item.getFeed().getId()
                        ).toList())
                )
                .orderBy(QContentFeed.contentFeed.id.desc())
                .limit(3)
                .fetch();
    }
}
