package org.doubleone.domain.senior.dto;

import java.nio.channels.MulticastChannel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


public record SeniorUpdateDto(
    Long seniorId,
    String careLevel,
    String address,
    MultipartFile imgFile,
    String etcDisease) {
}
