package org.doubleone.domain.Signup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.jwt.JwtToken;
import org.doubleone.domain.jwt.JwtTokenProvider;
import org.doubleone.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/manager")
    public ResponseEntity<String> signupManager(@RequestBody ManagerSignupDto request) {
        memberService.signUpManager(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/worker")
    public ResponseEntity<String> signupWorker(@RequestBody WorkerSignupDto request) {
        memberService.signUpWorker(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
    @PostMapping("/login")
    public JwtToken signIn(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        LoginService loginService = new LoginService(authenticationManager, jwtTokenProvider);
        log.info("INFO 로그: 프로세스 실행중");
        JwtToken jwtToken = loginService.signIn(email, password);

        return jwtToken;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

}
