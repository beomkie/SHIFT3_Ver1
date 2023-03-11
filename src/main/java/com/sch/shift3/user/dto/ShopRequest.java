package com.sch.shift3.user.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ShopRequest {
    private String location;
    private String subLocation;
    private String keyword;
    private String filter;

    private Double lat;
    private Double lng;

    public ShopRequest() {
        this.location = "내위치";
        this.subLocation = "";
        this.keyword = "";
        this.filter = "인기순";
    }

    public String getLocation() {
        switch(this.location){
            case "내위치" -> {
                return "내위치";
            }
            case "서울" -> {
                return "서울특별시";
            }
            case "세종" -> {
                return "세종특별자치시";
            }
            case "경기" -> {
                return "경기도";
            }
            case "인천" -> {
                return "인천광역시";
            }
            case "강원" -> {
                return "강원도";
            }
            case "충북" -> {
                return "충청북도";
            }
            case "충남" -> {
                return "충청남도";
            }
            case "대전" -> {
                return "대전광역시";
            }
            case "경북" -> {
                return "경상북도";
            }
            case "경남" -> {
                return "경상남도";
            }
            case "부산" -> {
                return "부산광역시";
            }
            case "울산" -> {
                return "울산광역시";
            }
            case "대구" -> {
                return "대구광역시";
            }
            case "광주" -> {
                return "광주광역시";
            }
            case "전북" -> {
                return "전라북도";
            }
            case "전남" -> {
                return "전라남도";
            }
            case "제주" -> {
                return "제주특별자치도";
            }
            default -> {
                return "내위치";
            }
        }
    }

    public String getSubLocation() {
        if (subLocation.isEmpty()){
            return "전체";
        }

        return subLocation;
    }

    public List<String> getFullLocationList() {
        if (this.subLocation.contains("/")) {
            // return list of location + " " + subLocation.split
            return Arrays.stream(this.subLocation.split("/"))
                    .map(subLocationItem -> this.getLocation() + " " + subLocationItem)
                    .toList();
        } else {
            return List.of(this.getLocation() + " " + this.subLocation);
        }
    }

    public boolean isEmpty(){
        return this.location.equals("내위치") && this.subLocation.isEmpty() && this.keyword.isEmpty();
    }
}
