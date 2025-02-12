package org.doubleone.domain.login;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> login(@RequestBody LoginDto request) {
        memberService.signUp(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}
