package org.doubleone.domain.senior.controller;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.senior.dto.SeniorRequestDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.domain.senior.service.SeniorService;
import org.doubleone.domain.worker.service.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/seniors")
@RequiredArgsConstructor
public class SeniorController {

    private final SeniorService seniorService;
    private final WorkerService workerService;
    private final SeniorRepository seniorRepository;

    @PostMapping
    public ResponseEntity<SeniorRequestDto> registerSenior(@RequestBody SeniorRequestDto seniorRequestDto) {
        SeniorRequestDto registeredSenior = seniorService.registerSenior(seniorRequestDto);
        return ResponseEntity.ok(registeredSenior);
    }

    @PatchMapping("/{seniorId}")
    public ResponseEntity<SeniorRequestDto> updateSenior(
            @PathVariable Long seniorId,
            @RequestBody SeniorUpdateDto seniorUpdateDto
    ) {
        SeniorRequestDto updatedSenior = seniorService.updateSenior(seniorId, seniorUpdateDto);
        return ResponseEntity.ok(updatedSenior);
    }

    @DeleteMapping("/{seniorId}")
    public ResponseEntity<Void> deleteSenior(@PathVariable Long seniorId) {
        seniorService.deleteSenior(seniorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SeniorResponseDto>> getSeniorList() {
        List<SeniorResponseDto> seniors = seniorService.getSeniorList();
        return ResponseEntity.ok(seniors);
    }

    @GetMapping("/{seniorId}")
    public ResponseEntity<SeniorResponseDto> getSeniorDetail(@PathVariable Long seniorId) {
        SeniorResponseDto senior = seniorService.getSeniorDetail(seniorId);
        return ResponseEntity.ok(senior);
    }

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

    //매칭된 요양사 아이디 가져오기
    @GetMapping("/match/{seniorId}")
    public ResponseEntity<List<Long>> findWorkersBySenior(@PathVariable Long seniorId) {
        // Senior 엔티티를 가져옴 (SeniorRepository는 기존에 있다고 가정)
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Senior not found"));

        // Worker ID 리스트 조회
        List<Long> workerIds = workerService.getWorkerIdsBySeniorAddress(senior);

        return ResponseEntity.ok(workerIds);
    }
}
