package org.doubleone.domain.address.controller;


import org.doubleone.domain.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService)
    {
        this.addressService = addressService;
    }

    // 도시별 구 리스트 반환 API
    @GetMapping("/city/{city}")
    public List<String> getDistrictsByCity(@PathVariable String city) {
        return addressService.getDistrictsByCity(city);
    }

    // 구/군별 행정동 리스트 반환 API
    @GetMapping("/district/{district}")
    public List<String> getNeighborhoodsByDistrict(@PathVariable String district) {
        return addressService.getNeighborhoodsByDistrict(district);
    }
}
