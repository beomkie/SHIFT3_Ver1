package com.sch.shift3.utill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class Geocoding {
    @Autowired
    private final RestTemplate restTemplate;
    private static final String API_KEY_ID = "2wow7wgs7e";
    private static final String API_KEY = "eJXSkRgM2AfYdJ8dKN4dXgxCGm9tPkQebElHlieU";

    public Address getLatLng(String roadAddr) {
        // get geo location
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", API_KEY_ID);
        headers.set("X-NCP-APIGW-API-KEY", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + roadAddr;

        ResponseEntity<MapGeoCodeResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, MapGeoCodeResponse.class);
        log.info("response: {}", response);

        if (response.getStatusCode().is2xxSuccessful()) {
            MapGeoCodeResponse mapGeoCodeResponse = response.getBody();
            double x = mapGeoCodeResponse.getAddresses().get(0).getX();
            double y = mapGeoCodeResponse.getAddresses().get(0).getY();
            return new Address(x, y);
        } else {
            return new Address(0, 0);
        }
    }
}
