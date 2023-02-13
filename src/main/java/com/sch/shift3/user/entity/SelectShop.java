package com.sch.shift3.user.entity;

import com.sch.shift3.user.dto.SelectShopDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "street_address", nullable = false, length = 100)
    private String streetAddress;

    @Column(name = "street_address_detail", length = 20)
    private String streetAddressDetail;

    @Column(name = "contact_number", nullable = false, length = 20)
    private String contactNumber;

    @Column(name = "operating_time")
    private String operatingTime;

    @Column(name = "hit_count")
    private Integer hitCount;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @Builder.Default
//    @JoinColumn(name = "select_shop_id")
    @OneToMany(mappedBy = "selectShop", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ImageSelectShop> imageSelectShops = new HashSet<>();

    @Builder.Default
//    @JoinColumn(name = "select_shop_id")
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
                .name(name)
                .introduce(introduce)
                .latitude(latitude)
                .longitude(longitude)
                .streetAddress(streetAddress)
                .streetAddressDetail(streetAddressDetail)
                .imageList(new ArrayList<>(imageSelectShops))
                .brand(selectShopBrands.stream().map(SelectShopBrand::getBrandName).toList())
                .contactNumber(contactNumber)
                .operatingTime(operatingTime)
                .hitCount(hitCount)
                .createdAt(createdAt)
                .build();
    }


    public void update(SelectShopDto selectShopDto) {
        this.name = selectShopDto.getName();
        this.introduce = selectShopDto.getIntroduce();
        this.streetAddress = selectShopDto.getStreetAddress();
        this.streetAddressDetail = selectShopDto.getStreetAddressDetail();
        this.contactNumber = selectShopDto.getContactNumber();
        this.operatingTime = selectShopDto.getOperatingTime();
        this.hitCount = selectShopDto.getHitCount();
    }

    public String getStreetAddressClear() {
        // replace "(%s")" to ""
        return streetAddress.replaceAll("\\(.*\\)", "");
    }

    public String toJson(){
        // json Object
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("introduce", introduce);
        jsonObject.put("latitude", latitude);
        jsonObject.put("longitude", longitude);
        jsonObject.put("streetAddress", streetAddress);
        jsonObject.put("streetAddressDetail", streetAddressDetail);
        jsonObject.put("contactNumber", contactNumber);
        jsonObject.put("operatingTime", operatingTime);
        jsonObject.put("hitCount", hitCount);
        jsonObject.put("createdAt", createdAt);
        jsonObject.put("streetAddressClear", getStreetAddressClear());

        JSONArray imageSelectShops = new JSONArray();
        this.imageSelectShops.forEach(imageSelectShop -> {
            JSONObject imageObj = new JSONObject();
            imageObj.put("imageName", imageSelectShop.getImageName());
            imageSelectShops.put(imageObj);
        });

        JSONArray brandArray = new JSONArray();
        this.selectShopBrands.forEach(selectShopBrand -> {
            JSONObject brandObj = new JSONObject();
            brandObj.put("brandName", selectShopBrand.getBrandName());
            brandArray.put(brandObj);
        });


        jsonObject.put("imageSelectShops", imageSelectShops);
        jsonObject.put("selectShopBrands", brandArray);

        return jsonObject.toString();
    }
}