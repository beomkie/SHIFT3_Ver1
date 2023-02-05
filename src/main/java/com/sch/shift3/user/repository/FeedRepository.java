package com.sch.shift3.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sch.shift3.user.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedRepository{
    private final JPAQueryFactory queryFactory;


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


}
