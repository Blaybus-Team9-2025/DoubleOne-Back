package org.doubleone.domain.Signup;

import org.doubleone.domain.jwt.JwtToken;
import org.doubleone.domain.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    // 🔹 생성자 주입을 사용하여 AuthenticationManager 주입
    @Autowired
    public LoginService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public JwtToken signIn(String email, String password) {

        //사용자 인증정보 감싸기
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        //메서드에 authenticationToken 전달, 인증수행
        Authentication authentication =  authenticationManager.authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }
}
