package org.doubleone.domain.condition.controller;

import lombok.RequiredArgsConstructor;
import org.doubleone.domain.condition.dto.ConditionRequestDto;
import org.doubleone.domain.condition.service.ConditionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers/conditions")
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;

    @PostMapping
    public ResponseEntity<Long> createCondition(@RequestBody ConditionRequestDto requestDto) {
        Long conditionId = conditionService.createCondition(requestDto);
        return ResponseEntity.ok(conditionId);
    }

    @PatchMapping("/{conditionId}")
    public ResponseEntity<Void> updateCondition(
            @PathVariable Long conditionId,
            @RequestBody ConditionRequestDto requestDto
    ) {
        conditionService.updateCondition(conditionId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{conditionId}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long conditionId) {
        conditionService.deleteCondition(conditionId);
        return ResponseEntity.noContent().build();
    }
}
