package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.SelectShop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * A DTO for the {@link SelectShop} entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DibProductDto {
    private Integer id;
    private Integer imageId;
    private String name;
    private Integer price;
    private String url;
    private Instant createdAt;
    private String imageName;
}