package org.doubleone.domain.senior.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.senior.service.SeniorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.doubleone.domain.senior.dto.SeniorDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Senior", description = "노인 정보 관리 API")
@RequiredArgsConstructor
@RequestMapping("/api/seniors")
public class SeniorController {

    private final SeniorService seniorService;

    // 어르신 정보 등록
    @PostMapping
    public ResponseEntity<SeniorDto> registerSenior(@RequestBody @Valid SeniorDto seniorDto) {
        log.info("어르신 정보 등록 요청: {}", seniorDto);
        SeniorDto createdSenior = seniorService.registerSenior(seniorDto);
        return ResponseEntity.created(URI.create("/api/seniors/" + createdSenior.getId()))
                .body(createdSenior);
    }

    // 어르신 정보 수정
    @PatchMapping("/{seniorId}")
    public ResponseEntity<SeniorDto> updateSenior(@PathVariable Long seniorId,
                                                  @RequestBody @Valid SeniorUpdateDto seniorUpdateDto) {
        log.info("어르신 정보 수정 요청: ID={}, Data={}", seniorId, seniorUpdateDto);
        return ResponseEntity.ok(seniorService.updateSenior(seniorId, seniorUpdateDto));
    }

    // 어르신 정보 삭제
    @DeleteMapping("/{seniorId}")
    public ResponseEntity<Void> deleteSenior(@PathVariable Long seniorId) {
        log.info("어르신 정보 삭제 요청: ID={}", seniorId);
        seniorService.deleteSenior(seniorId);
        return ResponseEntity.noContent().build();
    }

    // 어르신 정보 목록 조회
    @GetMapping
    public ResponseEntity<List<SeniorDto>> getAllSeniors() {
        log.info("어르신 정보 목록 조회 요청");
        return ResponseEntity.ok(seniorService.getAllSeniors());
    }
}
