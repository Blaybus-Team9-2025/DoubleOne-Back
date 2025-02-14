package org.doubleone.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    //클라이언트에 토큰 보낼 DTO
    private String grantType;//JWT에 대한 인증타입
    private String accessToken;
    private String refreshToken;
    //access token을 HTTP 요청의 Authorization 헤더에 포함하여 전송하는 방식.
}
