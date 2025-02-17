package org.doubleone.domain.manager.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.dto.CenterUpdateRequestDto;
import org.doubleone.domain.manager.dto.ManagerProfileUpdateRequestDto;
import org.doubleone.domain.manager.dto.ManagerUpdateRequestDto;
import org.doubleone.domain.manager.dto.SeniorMatchingResponseDto;
import org.doubleone.domain.manager.service.ManagerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Tag(name = "Manager")
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

//    // 개인정보 수정
//    @PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Void> updateProfile(@RequestPart(required = false) MultipartFile imgFile, @RequestBody ManagerUpdateRequestDto requestDto) {
//        managerService.updateProfile(imgFile, requestDto);
//        return ResponseEntity.noContent().build();
//    }

//  // 개인정보 수정
//  @PatchMapping("/profile")
//  public ResponseEntity<Void> updateProfile(
//      @AuthenticationPrincipal UserDetails userDetails,
//      @RequestBody ManagerProfileUpdateRequestDto requestDto
//  ) {
//    String managerEmail = userDetails.getUsername();
//    managerService.updateProfile(managerEmail, requestDto);
//    return ResponseEntity.noContent().build();
//  }

    // 센터정보 수정
    @PatchMapping(value = "/center-info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCenterInfo(@RequestPart(required = false) MultipartFile imgFile, @RequestBody CenterUpdateRequestDto requestDto) {
        managerService.updateCenterInfo(imgFile, requestDto);
        return ResponseEntity.noContent().build();
    }

    // 현재 매칭 중인 어르신 목록 조회
    @GetMapping("/matching-senior")
    public ResponseEntity<List<SeniorMatchingResponseDto>> getMatchingSeniors() {
        List<SeniorMatchingResponseDto> response = managerService.getMatchingSeniors();
        return ResponseEntity.ok(response);
    }
}
