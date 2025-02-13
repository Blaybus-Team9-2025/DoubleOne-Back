package org.doubleone.domain.senior.controller;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.senior.dto.SeniorResponseDto;
import org.doubleone.domain.senior.service.SeniorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seniors")
@RequiredArgsConstructor
public class SeniorController {

    private final SeniorService seniorService;

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

}
