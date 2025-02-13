package org.doubleone.domain.senior.dto;

import org.doubleone.domain.senior.entity.CohabitationStatus;
import org.doubleone.domain.senior.entity.Gender;

import java.time.LocalDate;
import java.util.Map;

public class SeniorDto {
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private int care_level;
    private int weight;
    private String address;
    private String profileImg;
    private CohabitationStatus cohabitationStatus;
    private Map<String, Object> dementiaSymptoms;
}
