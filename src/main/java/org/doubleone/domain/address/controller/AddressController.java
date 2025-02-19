package org.doubleone.domain.address.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final Map<String, List<String>> cityData = new HashMap<>();
    private final Map<String, List<String>> districtData = new HashMap<>();

    // 특정 도시의 지역 리스트 반환
    @GetMapping("/city/{cityName}")
    public List<String> getCityData(@PathVariable String cityName) {
        return cityData.getOrDefault(cityName, Collections.emptyList());
    }

    // 특정 구/지역의 하위 지역 리스트 반환
    @GetMapping("/district/{districtName}")
    public List<String> getDistrictData(@PathVariable String districtName) {
        return districtData.getOrDefault(districtName, Collections.emptyList());
    }

    // 새로운 도시와 지역추가(프론트연결X)
    @PostMapping("/city")
    public ResponseEntity<?> addCityData(@RequestBody Map<String, List<String>> newCityData) {
        for (Map.Entry<String, List<String>> entry : newCityData.entrySet()) {
            cityData.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 새로운 구와 하위지역 추가(프론트연결X)
    @PostMapping("/district")
    public ResponseEntity<?> addDistrictData(@RequestBody Map<String, List<String>> newDistrictData) {
        for (Map.Entry<String, List<String>> entry : newDistrictData.entrySet()) {
            districtData.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
