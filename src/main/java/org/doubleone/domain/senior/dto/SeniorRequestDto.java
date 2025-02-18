package org.doubleone.domain.senior.dto;


import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.member.entity.Gender;
import org.doubleone.domain.senior.entity.CareLevel;
import org.doubleone.domain.senior.entity.CohabitationStatus;
import org.doubleone.domain.senior.entity.Senior;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public record SeniorRequestDto (
    Long managerId,
    String name,
    String gender,
    String birthDate,
    String careLevel,
    int weight,
    String address,
    MultipartFile imgFile,
    String cohabitationStatus,
    List<String> dementiaSymptoms,
    String etcDisease){

    public Senior toEntity(Manager manager, String profileImg) {
        return Senior.builder()
                .manager(manager)
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
                .build();
    }
}
