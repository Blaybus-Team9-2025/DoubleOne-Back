package org.doubleone.domain.address.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city; // 도시

    @Column(nullable = false)
    private String district; // 구

    @Column(nullable = true)
    private String neighborhood; // 행정동(null ok)

    public Address() {}

    public Address(String city, String district, String neighborhood) {
        this.city = city;
        this.district = district;
        this.neighborhood = neighborhood;
    }


}
