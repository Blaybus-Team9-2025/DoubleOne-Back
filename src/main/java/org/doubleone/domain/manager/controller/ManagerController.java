package org.doubleone.domain.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.dto.CenterUpdateRequestDto;
import org.doubleone.domain.manager.dto.ManagerUpdateRequestDto;
import org.doubleone.domain.manager.dto.SeniorMatchingResponseDto;
import org.doubleone.domain.manager.service.ManagerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Manager")
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

    // 개인정보 수정
    @Operation(summary = "관리자 기본정보 조회", description = "관리자의 기본정보를 조회")
    @GetMapping("/{managerId}")
    public ResponseEntity<?> getManagerDetails(@PathVariable("managerId") Long managerId) {
        return ResponseEntity.ok(managerService.getManagerDetails(managerId));
    }

    // 개인정보 수정
    @Operation(summary = "요양사 기본정보 편집", description = "요양사의 기본정보를 편집")
    @PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProfile(@Valid @ModelAttribute ManagerUpdateRequestDto requestDto) {
        managerService.updateProfile(requestDto);
        return ResponseEntity.noContent().build();
    }

    // 센터정보 수정
    @Operation(summary = "요양사 기본정보 편집", description = "요양사의 기본정보를 편집")
    @PatchMapping(value = "/center-info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCenterInfo(@Valid @ModelAttribute CenterUpdateRequestDto requestDto) {
        managerService.updateCenterInfo(requestDto);
        return ResponseEntity.noContent().build();
    }

    // 현재 매칭 중인 어르신 목록 조회
    @Operation(summary = "요양사 기본정보 편집", description = "요양사의 기본정보를 편집")
    @GetMapping("/matching-senior")
    public ResponseEntity<List<SeniorMatchingResponseDto>> getMatchingSeniors() {
        List<SeniorMatchingResponseDto> response = managerService.getMatchingSeniors();
        return ResponseEntity.ok(response);
    }
}
