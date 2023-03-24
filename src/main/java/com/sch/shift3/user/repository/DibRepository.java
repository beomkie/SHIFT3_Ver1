package com.sch.shift3.user.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sch.shift3.user.entity.Dib;
import com.sch.shift3.user.entity.QDib;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public void dibProduct(int accountId, int productId) {
        QDib dib = QDib.dib;
        //check exist dib
        if (isProductDib(accountId, productId)) return;

        // createdAt
        queryFactory
                .insert(dib)
                .columns(dib.accountId, dib.productId)
                .values(accountId, productId)
                .execute();
    }

    public void dibShop(int accountId, int shopId) {
        QDib dib = QDib.dib;
        //check exist dib
        if (isShopDib(accountId, shopId)) return;

        queryFactory
                .insert(dib)
                .columns(dib.accountId, dib.selectShopId)
                .values(accountId, shopId)
                .execute();
    }

    public void removeDibProduct(Integer accountId, Integer productId) {
        QDib dib = QDib.dib;
        queryFactory
                .delete(dib)
                .where(dib.accountId.eq(accountId)
                        .and(dib.productId.eq(productId)))
                .execute();
    }

    public PageImpl<Dib> getDibProductList(int accountId, Pageable pageable) {
        QDib dib = QDib.dib;

        var expression = dib.accountId.eq(accountId)
                .and(dib.productId.isNotNull())
                .and(dib.selectShopId.isNull());


        JPAQuery<Dib> query = queryFactory
                .selectFrom(dib)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        OrderSpecifier<Integer> orderSpecifier = pageable.getSort().getOrderFor("id").isAscending() ? dib.id.asc() : dib.id.desc();
        query.orderBy(orderSpecifier);

        List<Dib> dibs = query.fetch();

        Long count = queryFactory
                .select(dib.count())
                .where(expression)
                .from(dib)
                .fetchOne();

        return new PageImpl<>(dibs, pageable, count);
    }

    public PageImpl<Dib> getDibSelectShopList(int accountId, Pageable pageable){
        QDib dib = QDib.dib;

        var expression = dib.accountId.eq(accountId)
                .and(dib.productId.isNull())
                .and(dib.selectShopId.isNotNull());

        JPAQuery<Dib> query = queryFactory
                .selectFrom(dib)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        OrderSpecifier<Integer> orderSpecifier = pageable.getSort().getOrderFor("id").isAscending() ? dib.id.asc() : dib.id.desc();
        query.orderBy(orderSpecifier);

        List<Dib> dibs = query.fetch();

        Long count = queryFactory
                .select(dib.count())
                .where(expression)
                .from(dib)
                .fetchOne();

        return new PageImpl<>(dibs, pageable, count);
    }
    public void removeDibShop(Integer id, Integer shopId) {
        QDib dib = QDib.dib;
        queryFactory
                .delete(dib)
                .where(dib.accountId.eq(id)
                        .and(dib.selectShopId.eq(shopId)))
                .execute();
    }

    public void removeAllShop(Integer id) {
        QDib dib = QDib.dib;
        queryFactory
                .delete(dib)
                .where(dib.selectShopId.eq(id)
                        .and(dib.productId.isNull()))
                .execute();
    }

    public void removeAllProduct(Integer id) {
        QDib dib = QDib.dib;
        queryFactory
                .delete(dib)
                .where(dib.productId.eq(id)
                        .and(dib.selectShopId.isNull()))
                .execute();
    }
}
