package com.sch.shift3.utill;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MapGeoCodeResponse {
    private List<Address> addresses;
}
