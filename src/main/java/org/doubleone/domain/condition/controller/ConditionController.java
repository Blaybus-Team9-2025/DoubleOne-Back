package org.doubleone.domain.condition.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.doubleone.domain.condition.dto.ConditionRequestDto;
import org.doubleone.domain.condition.dto.ConditionResponseDto;
import org.doubleone.domain.condition.service.ConditionService;
import org.doubleone.domain.worker.dto.request.WorkerConditionRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Condition")
@RestController
@RequestMapping("/managers/conditions")
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;

    @Operation(summary = "근무 조건 등록", description = "관리자가 노인에 대한 근무 조건을 등록")
    @PostMapping("/{seniorId}")
    public ResponseEntity<?> createCondition(@PathVariable("seniorId") Long seniorId, @RequestBody ConditionRequestDto requestDto) {
        conditionService.createCondition(seniorId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = "근무 조건 편집", description = "관리자가 노인에 대한 근무 조건을 편집")
    @PatchMapping("/{seniorId}/workerConditions/{seniorConditionId}")
    public ResponseEntity<?> updateCondition(@PathVariable("seniorId") Long seniorId, @PathVariable("seniorConditionId") Long seniorConditionId, @RequestBody @Valid ConditionRequestDto requestDto) {
        conditionService.updateCondition(seniorConditionId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "근무 조건 삭제", description = "관리자가 노인에 대한 근무 조건을 삭제")
    @DeleteMapping("/{conditionId}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long conditionId) {
        conditionService.deleteCondition(conditionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "근무 조건 상세정보 조회", description = "노인에 대한 근무 조건 상세정보 조회")
    @GetMapping("/{conditionId}")
    public ResponseEntity<ConditionResponseDto> getConditionDetail(@PathVariable Long conditionId) {
        ConditionResponseDto responseDto = conditionService.getConditionDetail(conditionId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "근무 조건 목록 조회", description = "관리자가 등록한 근무 조건 목록을 조회")
    @GetMapping
    public ResponseEntity<List<ConditionResponseDto>> getConditionList() {
        List<ConditionResponseDto> conditions = conditionService.getConditionList();
        return ResponseEntity.ok(conditions);
    }
}
