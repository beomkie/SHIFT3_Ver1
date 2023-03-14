package com.sch.shift3.user.entity;

import com.sch.shift3.user.dto.SelectShopDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "select_shop")
@EntityListeners(AuditingEntityListener.class)
public class SelectShop {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "introduce", length = 70)
    private String introduce;

    @Column(name = "introduce_sub", length = 10000)
    private String introduceSub;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "street_address", nullable = false, length = 150)
    private String streetAddress;

    @Column(name = "street_address_detail", length = 20)
    private String streetAddressDetail;

    @Column(name = "contact_number", nullable = false, length = 20)
    private String contactNumber;

    @Column(name = "operating_time")
    private String operatingTime;

    @Column(name = "break_time")
    private String breakTime;

    @Column(name = "hit_count")
    private Integer hitCount;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "selectShop", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ImageSelectShop> imageSelectShops = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "selectShop", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SelectShopBrand> selectShopBrands = new HashSet<>();

    public void addImageSelectShop(ImageSelectShop imageSelectShop) {
        imageSelectShops.add(imageSelectShop);
    }

    public void addSelectShopBrand(SelectShopBrand selectShopBrand) {
        selectShopBrands.add(selectShopBrand);
    }

    public SelectShopDto of() {
        return SelectShopDto.builder()
                .id(id)
                .name(escapeQuote(name))
                .introduce(escapeQuote(introduce))
                .introduceSub(escapeQuote(introduceSub))
                .latitude(latitude)
                .longitude(longitude)
                .streetAddress(escapeQuote(streetAddress))
                .streetAddressDetail(escapeQuote(streetAddressDetail))
                .imageList(new ArrayList<>(imageSelectShops))
                .brand(selectShopBrands.stream().map(SelectShopBrand::getBrandName).toList())
                .contactNumber(escapeQuote(contactNumber))
                .operatingTime(escapeQuote(operatingTime))
                .breakTime(escapeQuote(breakTime))
                .hitCount(hitCount)
                .createdAt(createdAt)
                .build();
    }


    public void update(SelectShopDto selectShopDto) {
        this.name = selectShopDto.getName();
        this.introduce = selectShopDto.getIntroduce();
        this.introduceSub = selectShopDto.getIntroduceSub();
        this.streetAddress = selectShopDto.getStreetAddress();
        this.streetAddressDetail = selectShopDto.getStreetAddressDetail();
        this.contactNumber = selectShopDto.getContactNumber();
        this.operatingTime = selectShopDto.getOperatingTime();
        this.breakTime = selectShopDto.getBreakTime();
        this.hitCount = selectShopDto.getHitCount();
    }

    public String getStreetAddressClear() {
        // replace "(%s")" to ""
        return streetAddress.replaceAll("\\(.*\\)", "");
    }

    public String escapeQuote(String str) {
        if (str == null){
            return null;
        }

        return str.replaceAll("[\"']", "&quot;")
                .replaceAll("'", "&#39;");
    }
}