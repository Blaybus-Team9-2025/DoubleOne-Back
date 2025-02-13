package org.doubleone.domain.senior.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.senior.entity.Senior;

@Getter
@NoArgsConstructor
public class SeniorRequestDto {

    private Long id;
    private String name;
    private String gender;
    private String birthDate;
    private String careLevel;
    private int weight;
    private String zipCode;
    private String detailedAddress;
    private String profileImg;
    private String cohabitationStatus;
    private String dementiaSymptoms;
    private String etcDisease;

    public static SeniorRequestDto fromEntity(Senior senior) {
        SeniorRequestDto dto = new SeniorRequestDto();
        dto.id = senior.getSeniorId();
        dto.name = senior.getName();
        dto.gender = senior.getGender().name();
        dto.birthDate = senior.getBirthDate().toString();
        dto.careLevel = senior.getCareLevel().name();
        dto.weight = senior.getWeight();
        dto.zipCode = senior.getZipCode();
        dto.detailedAddress = senior.getDetailedAddress();
        dto.profileImg = senior.getProfileImg();
        dto.cohabitationStatus = senior.getCohabitationStatus().name();
        dto.dementiaSymptoms = String.join(",", senior.getDementiaSymptoms());
        dto.etcDisease = senior.getEtcDisease();
        return dto;
    }
}
