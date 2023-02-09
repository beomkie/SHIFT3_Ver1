package com.sch.shift3.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sch.shift3.user.entity.Dib;
import com.sch.shift3.user.entity.QDib;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DibRepository {
    private final JPAQueryFactory queryFactory;

    public List<Dib> myShopDibList(int accountId) {
        QDib dib = QDib.dib;
        return queryFactory
                .selectFrom(dib)
                .where(dib.accountId.eq(accountId)
                        .and(dib.selectShopId.isNotNull()))
                // join
                .fetch();
    }

    public boolean isProductDib(int accountId, int productId) {
        if (accountId == 0) return false; // 비회원일 경우 (비회원은 상품 찜 불가

        QDib dib = QDib.dib;
        return queryFactory
                .selectFrom(dib)
                .where(dib.accountId.eq(accountId)
                        .and(dib.productId.eq(productId)))
                .fetchFirst() != null;
    }

    public boolean isShopDib(int accountId, int shopId) {
        if (accountId == 0) return false; // 비회원일 경우 (비회원은 상품 찜 불가

        QDib dib = QDib.dib;
        return queryFactory
                .selectFrom(dib)
                .where(dib.accountId.eq(accountId)
                        .and(dib.selectShopId.eq(shopId)))
                .fetchFirst() != null;
    }

}
