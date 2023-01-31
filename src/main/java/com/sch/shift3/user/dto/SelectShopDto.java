package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.SelectShop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link SelectShop} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class SelectShopDto{
    private Integer id;
    private String name;
    private String introduce;
    private Double latitude;
    private Double longitude;
    private String streetAddress;
    private String streetAddressDetail;
    private String contactNumber;
    private String operatingTime;
    private Integer hitCount;
    private Instant createdAt;

//    @Builder.Default
    private List<String> brand = new ArrayList<>();

//    @Builder.Default
    private List<MultipartFile> images = new ArrayList<>();

    public SelectShop toEntity(){
        return SelectShop.builder()
            .id(id)
            .name(name)
            .introduce(introduce)
            .latitude(latitude)
            .longitude(longitude)
            .streetAddress(streetAddress)
            .streetAddressDetail(streetAddressDetail)
            .contactNumber(contactNumber)
            .operatingTime(operatingTime)
            .hitCount(hitCount)
            .createdAt(createdAt)
            .build();
    }
}