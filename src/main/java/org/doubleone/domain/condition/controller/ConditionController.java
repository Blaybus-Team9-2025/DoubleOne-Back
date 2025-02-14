package org.doubleone.domain.condition.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.doubleone.domain.condition.dto.ConditionRequestDto;
import org.doubleone.domain.condition.dto.ConditionResponseDto;
import org.doubleone.domain.condition.service.ConditionService;
import org.doubleone.domain.worker.dto.request.WorkerConditionRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers/conditions")
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;


    @PostMapping("/{seniorId}")
    public ResponseEntity<?> createCondition(@PathVariable("seniorId") Long seniorId, @RequestBody ConditionRequestDto requestDto) {
        conditionService.createCondition(seniorId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{seniorId}/workerConditions/{seniorConditionId}")
    public ResponseEntity<?> updateCondition(@PathVariable("seniorId") Long seniorId, @PathVariable("seniorConditionId") Long seniorConditionId, @RequestBody @Valid ConditionRequestDto requestDto) {
        conditionService.updateCondition(seniorConditionId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{conditionId}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long conditionId) {
        conditionService.deleteCondition(conditionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{conditionId}")
    public ResponseEntity<ConditionResponseDto> getConditionDetail(@PathVariable Long conditionId) {
        ConditionResponseDto responseDto = conditionService.getConditionDetail(conditionId);
        return ResponseEntity.ok(responseDto);
    }

}
