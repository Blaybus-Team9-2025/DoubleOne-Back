package org.doubleone.domain.worker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.doubleone.domain.worker.dto.request.WorkerConditionRequestDto;
import org.doubleone.domain.workerCondition.service.WorkerConditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Worker")
@RequiredArgsConstructor
@RequestMapping("/workers")
public class WorkerController {

  private final WorkerConditionService workerConditionService;

  @Operation(summary = "희망 근무 조건 등록", description = "요양사의 희망 근무 조건을 등록")
  @PostMapping("/{workerId}/workerConditions")
  public ResponseEntity<?> createWorkerCondition(@PathVariable("workerId") Long workerId, @RequestBody @Valid WorkerConditionRequestDto requestDto) {
    workerConditionService.createWorkerCondition(workerId, requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "희망 근무 조건 편집", description = "요양사의 희망 근무 조건을 편집")
  @PatchMapping("/{workerId}/workerConditions/{workerConditionId}")
  public ResponseEntity<?> updateWorkerCondition(@PathVariable("workerId") Long workerId, @PathVariable("workerConditionId") Long workerConditionId, @RequestBody @Valid WorkerConditionRequestDto requestDto) {
    workerConditionService.updateWorkerCondition(workerConditionId, requestDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Operation(summary = "희망 근무 조건 삭제", description = "요양사의 희망 근무 조건을 삭제")
  @DeleteMapping("/{workerId}/workerConditions/{workerConditionId}")
  public ResponseEntity<?> deleteWorkerCondition(@PathVariable("workerId") Long workerId, @PathVariable("workerConditionId") Long workerConditionId) {
    workerConditionService.deleteWorkerCondition(workerConditionId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
