package org.doubleone.domain.senior.dto;


import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.member.entity.Gender;
import org.doubleone.domain.senior.entity.CareLevel;
import org.doubleone.domain.senior.entity.CohabitationStatus;
import org.doubleone.domain.senior.entity.Senior;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeniorRequestDto {
    private Long managerId;
    private String name;
    private String gender;
    private String birthDate;
    private String careLevel;
    private int height; //
    private int weight;
    private String address;
    private MultipartFile imgFile;
    private String cohabitationStatus;
    private List<String> dementiaSymptoms;
    private String etcDisease;

    public Senior toEntity(Manager manager, String profileImg) {
        return Senior.builder()
                .manager(manager)
                .name(name)
                .gender(Gender.valueOf(gender.toUpperCase()))
                .birthDate(LocalDate.parse(birthDate))
                .careLevel(CareLevel.valueOf(careLevel.toUpperCase()))
                .height(height) //
                .weight(weight)
                .address(address)
                .profileImg(profileImg)
                .cohabitationStatus(CohabitationStatus.valueOf(cohabitationStatus.toUpperCase()))
                .dementiaSymptoms(dementiaSymptoms)
                .etcDisease(etcDisease)
                .matchingStatus(MatchingStatus.BEFORE_REQUEST)
                .build();
    }
}
