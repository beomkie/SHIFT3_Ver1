package com.sch.shift3.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sch.shift3.user.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        // increase hit count
        ContentFeed feed = contentFeedRepository.findById(id).orElse(null);
        if(feed != null){
            feed.increaseHit();
            contentFeedRepository.save(feed);
        }

        return queryFactory
                .selectFrom(QContentFeed.contentFeed)
                .where(QContentFeed.contentFeed.id.eq(id))
                .fetchOne();
    }

    public PageImpl<ContentFeed> searchFeed(String keyword, String category, Pageable pageable) {
        QContentFeed feed = QContentFeed.contentFeed;
        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null) {
            builder.and(feed.title.contains(keyword)
                        .or(feed.description.contains(keyword)));
        }

        if (category != null && !category.equals("전체")) {
            builder.and(feed.category.eq(category));
        }

        OrderSpecifier<?> orderSpecifier = feed.id.desc();

        if (pageable.getSort().getOrderFor("id") != null){
            orderSpecifier = pageable.getSort().getOrderFor("id").isAscending() ?  feed.id.asc() : feed.id.desc();
        } else if (pageable.getSort().getOrderFor("hit") != null){
            orderSpecifier = pageable.getSort().getOrderFor("hit").isAscending() ?  feed.hit.asc() : feed.hit.desc();
        } else if (pageable.getSort().getOrderFor("createdAt") != null){
            orderSpecifier = pageable.getSort().getOrderFor("createdAt").isAscending() ?  feed.createdAt.asc() : feed.createdAt.desc();
        }

        List<ContentFeed> feeds = queryFactory
                .selectFrom(feed)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();


        Long count = queryFactory
                .select(feed.count())
                .where(builder)
                .from(feed)
                .fetchOne();

        return new PageImpl<>(feeds, pageable, count);
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

    public List<ContentFeed> getBannerFeed() {
        return queryFactory
                .selectFrom(QContentFeed.contentFeed)
                .where(QContentFeed.contentFeed.isBanner.eq(true))
                .orderBy(QContentFeed.contentFeed.id.desc())
                .fetch();
    }
}
