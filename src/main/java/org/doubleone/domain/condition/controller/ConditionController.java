package org.doubleone.domain.condition.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.doubleone.domain.condition.dto.ConditionRequestDto;
import org.doubleone.domain.condition.dto.ConditionResponseDto;
import org.doubleone.domain.condition.service.ConditionService;
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
    public ResponseEntity<Long> createCondition(@PathVariable Long seniorId, @RequestBody ConditionRequestDto requestDto) {
        Long conditionId = conditionService.createCondition(seniorId, requestDto);
        return ResponseEntity.ok(conditionId); // conditionId 반환하도록 변경
    }


    @Operation(summary = "근무 조건 편집", description = "관리자가 노인에 대한 근무 조건을 편집")
    @PatchMapping("/{seniorId}/workerConditions/{seniorConditionId}")
    public ResponseEntity<Void> updateCondition(@PathVariable Long seniorId, @PathVariable Long seniorConditionId, @RequestBody @Valid ConditionRequestDto requestDto) {
        conditionService.updateCondition(seniorConditionId, requestDto);
        return ResponseEntity.ok().build();
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
        return ResponseEntity.ok(conditionService.getConditionDetail(conditionId));
    }

//    @Operation(summary = "근무 조건 목록 필터 조회", description = "관리자가 등록한 근무 조건 목록을 조회 (필터 가능)")
//    @GetMapping("/{managerId}/filters")
//    public ResponseEntity<List<ConditionResponseDto>> getConditionFilterList(@PathVariable Long managerId, @RequestParam(required = false) String filter) {
//        return ResponseEntity.ok(conditionService.getConditionFilterList(managerId, filter));
//    }

    @Operation(summary = "근무 조건 목록 필터 조회", description = "관리자가 등록한 근무 조건 목록을 조회 (필터 가능)")
    @GetMapping("/{managerId}/filters")
    public ResponseEntity<?> getConditionList(@PathVariable Long managerId, @RequestParam(required = false) String filter) {
        return ResponseEntity.ok(conditionService.getConditionList(managerId, filter));
    }
}
