package org.doubleone.domain.member.service;

import org.doubleone.domain.member.entity.Member;
import org.doubleone.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return memberRepository.findByEmail(email)
        .map(this::createUserDetails)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
  }

  // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
  private UserDetails createUserDetails(Member member) {
    return User.builder()
        .username(member.getEmail())
        .password(member.getPassword())
        .build();
  }

}
