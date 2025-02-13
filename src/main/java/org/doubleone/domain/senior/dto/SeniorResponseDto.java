package org.doubleone.domain.senior.dto;

import lombok.Getter;
import org.doubleone.domain.senior.entity.Senior;

import java.time.LocalDate;
import java.time.Period;

@Getter
public class SeniorResponseDto {
    private Long seniorId;
    private String name;
    private int age;
    private String gender;
    private String address;

    public SeniorResponseDto(Senior senior) {
        this.seniorId = senior.getSeniorId();
        this.name = senior.getName();
        this.age = calculateAge(senior.getBirthDate());
        this.gender = senior.getGender().name();
        this.address = senior.getAddress();
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
