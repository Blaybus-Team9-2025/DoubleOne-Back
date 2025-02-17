package org.doubleone.domain.senior.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.doubleone.domain.senior.dto.SeniorRequestDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.domain.senior.service.SeniorService;
import org.doubleone.domain.worker.dto.response.WorkerMatchResponseDto;
import org.doubleone.domain.worker.service.WorkerMatchService;
import org.doubleone.global.exception.CustomException;
import org.doubleone.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Senior")
@RestController
@RequestMapping("/seniors")
@RequiredArgsConstructor
public class SeniorController {

    private final SeniorService seniorService;
    private final SeniorRepository seniorRepository;
    private final WorkerMatchService workerMatchService;

    @Operation(summary = "노인 정보 등록", description = "관리자가 노인 정보를 등록")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SeniorRequestDto> registerSenior(@RequestPart(required = false) MultipartFile imgFile, @RequestBody SeniorRequestDto seniorRequestDto) {
        seniorService.registerSenior(imgFile, seniorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "노인 정보 편집", description = "관리자가 노인 정보를 편집")
    @PatchMapping(value = "/{seniorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SeniorRequestDto> updateSenior(
            @RequestPart(required = false) MultipartFile imgFile,
            @PathVariable Long seniorId,
            @RequestBody SeniorUpdateDto seniorUpdateDto
    ) {
        SeniorRequestDto updatedSenior = seniorService.updateSenior(imgFile, seniorId, seniorUpdateDto);
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

    @Operation(summary = "매칭된 요양사 확인")
    @GetMapping("/match/{seniorId}")
    public ResponseEntity<WorkerMatchResponseDto> findMatchedWorkers(@PathVariable Long seniorId)
    {
        WorkerMatchResponseDto responseList = workerMatchService.findWorkersBySenior(seniorId);
        return ResponseEntity.ok(responseList);

    }

}
