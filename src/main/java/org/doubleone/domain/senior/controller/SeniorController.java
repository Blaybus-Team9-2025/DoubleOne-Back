package org.doubleone.domain.senior.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.doubleone.domain.senior.dto.SeniorRequestDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.service.SeniorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Senior")
@RestController
@RequestMapping("/seniors")
@RequiredArgsConstructor
public class SeniorController {

    private final SeniorService seniorService;

    @Operation(summary = "노인 정보 등록", description = "관리자가 노인 정보를 등록")
    @PostMapping
    public ResponseEntity<SeniorRequestDto> registerSenior(@RequestBody SeniorRequestDto seniorRequestDto) {
        SeniorRequestDto registeredSenior = seniorService.registerSenior(seniorRequestDto);
        return ResponseEntity.ok(registeredSenior);
    }

    @Operation(summary = "노인 정보 편집", description = "관리자가 노인 정보를 편집")
    @PatchMapping("/{seniorId}")
    public ResponseEntity<SeniorRequestDto> updateSenior(
            @PathVariable Long seniorId,
            @RequestBody SeniorUpdateDto seniorUpdateDto
    ) {
        SeniorRequestDto updatedSenior = seniorService.updateSenior(seniorId, seniorUpdateDto);
        return ResponseEntity.ok(updatedSenior);
    }

    @Operation(summary = "노인 정보 삭제")
    @DeleteMapping("/{seniorId}")
    public ResponseEntity<Void> deleteSenior(@PathVariable Long seniorId) {
        seniorService.deleteSenior(seniorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "노인 정보 목록 조회")
    @GetMapping
    public ResponseEntity<List<SeniorResponseDto>> getSeniorList() {
        List<SeniorResponseDto> seniors = seniorService.getSeniorList();
        return ResponseEntity.ok(seniors);
    }

    @Operation(summary = "노인 정보 상세조회")
    @GetMapping("/{seniorId}")
    public ResponseEntity<SeniorResponseDto> getSeniorDetail(@PathVariable Long seniorId) {
        SeniorResponseDto senior = seniorService.getSeniorDetail(seniorId);
        return ResponseEntity.ok(senior);
    }

    @Operation(summary = "노인 정보 목록 필터 조회")
    @GetMapping("/filter")
    public ResponseEntity<List<SeniorResponseDto>> getSeniorListWithFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String region
    ) {
        List<SeniorResponseDto> seniors = seniorService.getSeniorListWithFilter(name, age, gender, region);
        return ResponseEntity.ok(seniors);
    }
}
