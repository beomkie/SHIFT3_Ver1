package com.sch.shift3.user.entity;

import com.sch.shift3.user.dto.SelectShopDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

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

    public SelectShopDto of() {
        return SelectShopDto.builder()
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