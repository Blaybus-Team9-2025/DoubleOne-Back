package org.doubleone.domain.address.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String city;


}
