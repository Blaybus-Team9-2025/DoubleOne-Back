package org.doubleone.domain.senior.dto;

import lombok.Getter;
import org.doubleone.domain.senior.entity.Senior;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter
public class SeniorResponseDto {
    private Long seniorId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String birthDate;
    private String careLevel;
    private int height;
    private int weight;
    private String profileImg;
    private String cohabitationStatus;
    private List<String> dementiaSymptoms;
    private String etcDisease;
    private String matchingStatus;

    public SeniorResponseDto(Senior senior) {
        this.seniorId = senior.getSeniorId();
        this.name = senior.getName();
        this.age = calculateAge(senior.getBirthDate());
        this.gender = senior.getGender().name();
        this.address = senior.getAddress();
        this.birthDate = senior.getBirthDate().toString();
        this.careLevel = senior.getCareLevel().name();
        this.height = senior.getHeight();
        this.weight = senior.getWeight();
        this.profileImg = senior.getProfileImg();
        this.cohabitationStatus = senior.getCohabitationStatus().name();
        this.dementiaSymptoms = senior.getDementiaSymptoms();
        this.etcDisease = senior.getEtcDisease();
        this.matchingStatus = senior.getMatchingStatus().name();
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
