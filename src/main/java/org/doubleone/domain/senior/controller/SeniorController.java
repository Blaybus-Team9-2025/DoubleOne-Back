package org.doubleone.domain.senior.controller;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.senior.dto.SeniorRequestDto;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.dto.SeniorUpdateDto;
import org.doubleone.domain.senior.service.SeniorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seniors")
@RequiredArgsConstructor
public class SeniorController {

    private final SeniorService seniorService;

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
}
