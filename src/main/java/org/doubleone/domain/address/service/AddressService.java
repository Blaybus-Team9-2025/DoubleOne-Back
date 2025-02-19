package org.doubleone.domain.address.service;

import org.doubleone.domain.address.entity.Address;
import org.doubleone.domain.address.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository)
    {
        this.addressRepository = addressRepository;
    }

    // 도시별 구 리스트 반환
    public List<String> getDistrictsByCity(String city) {
        return addressRepository.findByCity(city).stream()
                .map(Address::getDistrict)
                .distinct()
                .collect(Collectors.toList());
    }

    // 구별 행정동 리스트 반환
    public List<String> getNeighborhoodsByDistrict(String district) {
        return addressRepository.findByDistrict(district).stream()
                .map(Address::getNeighborhood)
                .distinct()
                .collect(Collectors.toList());
    }
}
