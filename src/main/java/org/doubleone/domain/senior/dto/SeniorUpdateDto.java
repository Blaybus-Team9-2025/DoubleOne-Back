package org.doubleone.domain.senior.dto;

import java.nio.channels.MulticastChannel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class SeniorUpdateDto {
    private String careLevel;
    private String address;
    private MultipartFile profileImg;
    private String etcDisease;
}
