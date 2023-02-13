package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDto {
    private List<Product> products;
    private int totalPage;
    private int totalSize;
    private int currentPage;
}
