package org.doubleone.domain.senior.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public record SeniorUpdateDto(
        Long seniorId,
        String careLevel,
        String address,
        String etcDisease,
        MultipartFile imgFile
) {}
