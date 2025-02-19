package org.doubleone.domain.senior.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.doubleone.domain.senior.dto.SeniorRequestDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.service.SeniorService;
import org.doubleone.domain.worker.dto.response.WorkerMatchResponseDto;
import org.doubleone.domain.worker.service.WorkerMatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Senior")
@RestController
@RequestMapping("/seniors")
@RequiredArgsConstructor
public class SeniorController {

    private final SeniorService seniorService;
    private final WorkerMatchService workerMatchService;

    @Operation(summary = "노인 정보 등록", description = "관리자가 노인 정보를 등록")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> registerSenior(
            @RequestPart("data") SeniorRequestDto seniorRequestDto, // JSON 데이터
            @RequestPart(value = "imgFile", required = false) MultipartFile imgFile // 파일
    ) {
        seniorRequestDto.setImgFile(imgFile); // 파일 따로 세팅
        seniorService.registerSenior(seniorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{seniorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateSenior(
            @PathVariable Long seniorId,
            @RequestPart("data") SeniorUpdateDto seniorUpdateDto, // JSON 데이터 받기
            @RequestPart(value = "imgFile", required = false) MultipartFile imgFile // 파일 받기
    ) {
        seniorUpdateDto.setSeniorId(seniorId); // ID 설정
        seniorUpdateDto.setImgFile(imgFile); // 파일 설정
        seniorService.updateSenior(seniorUpdateDto);
        return ResponseEntity.ok().build();
    }



    @Operation(summary = "노인 정보 삭제")
    @DeleteMapping("/{seniorId}")
    public ResponseEntity<Void> deleteSenior(@PathVariable Long seniorId) {
        seniorService.deleteSenior(seniorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "노인 정보 목록 조회")
    @GetMapping
    public ResponseEntity<List<SeniorResponseDto>> getSeniorList(
            @RequestParam(required = false) String sort
    ) {
        List<SeniorResponseDto> seniors = seniorService.getSeniorList(sort);
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
    @GetMapping("/match/{conditionId}")
    public ResponseEntity<WorkerMatchResponseDto> findMatchedWorkers(@PathVariable Long conditionId) {
        WorkerMatchResponseDto responseList = workerMatchService.findWorkersBySenior(conditionId);
        return ResponseEntity.ok(responseList);
    }
}
