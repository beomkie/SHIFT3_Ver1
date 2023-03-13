package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.ImageSelectShop;
import com.sch.shift3.user.entity.SelectShop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link SelectShop} entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class SelectShopDto{
    private Integer id;
    private String name;
    private String introduce;
    private String introduceSub;
    private Double latitude;
    private Double longitude;
    private String streetAddress;
    private String streetAddressDetail;
    private String contactNumber;
    private String operatingTime;
    private String breakTime;
    private Integer hitCount;
    private Instant createdAt;

    @Builder.Default
    private List<ImageSelectShop> imageList = new ArrayList<>();

    @Builder.Default
    private List<String> brand = new ArrayList<>();

    public SelectShop toEntity(){
        return SelectShop.builder()
            .id(id)
            .name(escapeQuote(name))
            .introduce(escapeQuote(introduce))
            .introduceSub(escapeQuote(introduceSub))
            .latitude(latitude)
            .longitude(longitude)
            .streetAddress(escapeQuote(streetAddress))
            .streetAddressDetail(escapeQuote(streetAddressDetail))
            .contactNumber(escapeQuote(contactNumber))
            .operatingTime(escapeQuote(operatingTime))
            .breakTime(escapeQuote(breakTime))
            .hitCount(hitCount)
            .createdAt(createdAt)
            .build();
    }

    public String escapeQuote(String str) {
        if (str == null){
            return null;
        }
        return str.replaceAll("[\"']", "&quot;")
                .replaceAll("'", "&#39;");
    }
}