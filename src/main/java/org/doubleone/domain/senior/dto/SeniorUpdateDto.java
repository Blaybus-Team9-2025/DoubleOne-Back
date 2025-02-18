package org.doubleone.domain.senior.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class SeniorUpdateDto {
    private Long seniorId;
    private String careLevel;
    private String address;
    private String etcDisease;
    private MultipartFile imgFile;
}
