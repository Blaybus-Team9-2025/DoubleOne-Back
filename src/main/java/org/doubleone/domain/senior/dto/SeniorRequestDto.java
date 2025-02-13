package org.doubleone.domain.senior.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.manager.entity.Gender;
import org.doubleone.domain.senior.entity.CareLevel;
import org.doubleone.domain.senior.entity.CohabitationStatus;
import org.doubleone.domain.senior.entity.Senior;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class SeniorRequestDto {

    private Long id;
    private String name;
    private String gender;
    private String birthDate;
    private String careLevel;
    private int weight;
    private String address;
    private String profileImg;
    private String cohabitationStatus;
    private String dementiaSymptoms;
    private String etcDisease;

    public Senior toEntity() {
        return Senior.builder()
                .name(this.name)
                .gender(Gender.valueOf(this.gender.toUpperCase()))
                .birthDate(LocalDate.parse(this.birthDate))
                .careLevel(CareLevel.valueOf(this.careLevel.toUpperCase()))
                .weight(this.weight)
                .address(this.address)
                .profileImg(this.profileImg)
                .cohabitationStatus(CohabitationStatus.valueOf(this.cohabitationStatus.toUpperCase()))
                .dementiaSymptoms(List.of(this.dementiaSymptoms.split(",")))
                .etcDisease(this.etcDisease)
                .build();
    }

    public static SeniorRequestDto fromEntity(Senior senior) {
        SeniorRequestDto dto = new SeniorRequestDto();
        dto.id = senior.getSeniorId();
        dto.name = senior.getName();
        dto.gender = senior.getGender().name();
        dto.birthDate = senior.getBirthDate().toString();
        dto.careLevel = senior.getCareLevel().name();
        dto.weight = senior.getWeight();
        dto.address = senior.getAddress();
        dto.profileImg = senior.getProfileImg();
        dto.cohabitationStatus = senior.getCohabitationStatus().name();
        dto.dementiaSymptoms = String.join(",", senior.getDementiaSymptoms());
        dto.etcDisease = senior.getEtcDisease();
        return dto;
    }
}
