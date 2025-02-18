package org.doubleone.domain.senior.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doubleone.domain.manager.entity.Gender;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.senior.entity.CareLevel;
import org.doubleone.domain.senior.entity.CohabitationStatus;
import org.doubleone.domain.senior.entity.Senior;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class SeniorRequestDto {
    private String name;
    private String gender;
    private String birthDate;
    private String careLevel;
    private int weight;
    private String address;
    private String profileImg;
    private String cohabitationStatus;
    private List<String> dementiaSymptoms;
    private String etcDisease;

    public Senior toEntity() {
        return Senior.builder()
                .name(name)
                .gender(Gender.valueOf(gender.toUpperCase()))
                .birthDate(LocalDate.parse(birthDate))
                .careLevel(CareLevel.valueOf(careLevel.toUpperCase()))
                .weight(weight)
                .address(address)
                .profileImg(profileImg)
                .cohabitationStatus(CohabitationStatus.valueOf(cohabitationStatus.toUpperCase()))
                .dementiaSymptoms(dementiaSymptoms)
                .etcDisease(etcDisease)
                .matchingStatus(MatchingStatus.BEFORE_REQUEST)
                .build();
    }

    public static SeniorRequestDto fromEntity(Senior senior) {
        SeniorRequestDto dto = new SeniorRequestDto();
        dto.name = senior.getName();
        dto.gender = senior.getGender().name();
        dto.birthDate = senior.getBirthDate().toString();
        dto.careLevel = senior.getCareLevel().name();
        dto.weight = senior.getWeight();
        dto.address = senior.getAddress();
        dto.profileImg = senior.getProfileImg();
        dto.cohabitationStatus = senior.getCohabitationStatus().name();
        dto.dementiaSymptoms = senior.getDementiaSymptoms();
        dto.etcDisease = senior.getEtcDisease();
        return dto;
    }
}
