package org.doubleone.domain.Signup;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.jwt.JwtToken;
import org.doubleone.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

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
        JwtToken jwtToken = memberService.signIn(email, password);
        return jwtToken;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

}
