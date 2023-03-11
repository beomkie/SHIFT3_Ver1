package com.sch.shift3.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sch.shift3.user.dto.ShopRequest;
import com.sch.shift3.user.entity.QSelectShop;
import com.sch.shift3.user.entity.SelectShop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShopRepository {
    private final JPAQueryFactory queryFactory;

    public List<SelectShop> getShopList(ShopRequest shopRequest) {
        QSelectShop qSelectShop = QSelectShop.selectShop;
        JPAQuery<SelectShop> query = queryFactory.selectFrom(qSelectShop);
        query.leftJoin(qSelectShop.imageSelectShops).fetchJoin();
        query.leftJoin(qSelectShop.selectShopBrands).fetchJoin();

        BooleanBuilder builder = new BooleanBuilder();

        if (shopRequest.getLocation().equals("내위치")) {
            // 내 위치 반경 5km
            builder.and(Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                    Expressions.stringTemplate("POINT({0}, {1})",
                            shopRequest.getLng(),
                            shopRequest.getLat()
                    ),
                    Expressions.stringTemplate("POINT({0}, {1})",
                            qSelectShop.longitude,
                            qSelectShop.latitude
                    )).lt(String.valueOf(5000)));
        } else {
            if (shopRequest.getLocation().equals("서울특별시")) {
                // subLocation 검사
                if (shopRequest.getSubLocation().equals("전체")) {
                    builder.and(qSelectShop.streetAddress.contains(shopRequest.getLocation()));
                } else {
                    BooleanBuilder subLocationBuilder = new BooleanBuilder();
                    List<String> fullLocationList = shopRequest.getFullLocationList();
                    log.info("fullLocationList: {}", fullLocationList);
                    fullLocationList.forEach(fullLocation -> subLocationBuilder.or(qSelectShop.streetAddress.contains(fullLocation)));
                    builder.and(subLocationBuilder);
                }
            } else {
                builder.and(qSelectShop.streetAddress.contains(shopRequest.getLocation()));
            }
        }

        if (!shopRequest.getKeyword().isBlank()) {
            builder.and(qSelectShop.name.contains(shopRequest.getKeyword()));
        }

        query.where(builder);

        switch (shopRequest.getFilter()) {
            case "인기순" -> query.orderBy(qSelectShop.hitCount.desc().nullsLast());
//            case "최신순" -> query.orderBy(qSelectShop.id.desc());
            case "거리순" -> query.orderBy(Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                    Expressions.stringTemplate("POINT({0}, {1})",
                            shopRequest.getLat(),
                            shopRequest.getLng()
                    ),
                    Expressions.stringTemplate("POINT({0}, {1})",
                            qSelectShop.longitude,
                            qSelectShop.latitude
                    )).asc());
            default -> query.orderBy(qSelectShop.id.desc());
        }
        return query.fetch();
    }


    public SelectShop getShopById(Integer shopId) {
        QSelectShop qSelectShop = QSelectShop.selectShop;
        JPAQuery<SelectShop> query = queryFactory.selectFrom(qSelectShop);
        query.leftJoin(qSelectShop.imageSelectShops).fetchJoin();
        query.leftJoin(qSelectShop.selectShopBrands).fetchJoin();
        query.where(qSelectShop.id.eq(shopId));
        return query.fetchOne();
    }
}
