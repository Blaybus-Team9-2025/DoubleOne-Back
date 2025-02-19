package org.doubleone.domain.worker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.worker.dto.request.WorkerUpdateRequest;
import org.doubleone.domain.worker.dto.response.WorkerDetailResponse;
import org.doubleone.domain.worker.dto.request.WorkerConditionRequestDto;
import org.doubleone.domain.worker.dto.response.WorkerPreferenceDto;
import org.doubleone.domain.worker.service.WorkerService;
import org.doubleone.domain.workerCondition.service.WorkerConditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Worker")
@RequiredArgsConstructor
@RequestMapping("/workers")
public class WorkerController {

    private final WorkerService workerService;
    private final WorkerConditionService workerConditionService;

    @Operation(summary = "희망 근무 조건 등록", description = "요양사의 희망 근무 조건을 등록")
    @PostMapping("/{workerId}/workerConditions")
    public ResponseEntity<Long> createWorkerCondition(@PathVariable("workerId") Long workerId, @RequestBody @Valid WorkerConditionRequestDto requestDto) {
        Long workerConditionId = workerConditionService.createWorkerCondition(workerId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(workerConditionId);
    }

    // 희망 근무 조건 조회
    @Operation(summary = "희망 근무 조건 조회", description = "요양사의 희망 근무 조건을 조회")
    @GetMapping("/{workerId}/workerConditions/{workerConditionId}")
    public ResponseEntity<WorkerPreferenceDto> readWorkerCondition(@PathVariable("workerId") Long workerId, @PathVariable Long workerConditionId){
        WorkerPreferenceDto response = workerConditionService.readWorkerCondition(workerConditionId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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

    // 요양사 상세 정보 조회
    @Operation(summary = "요양사 상세 정보 조회", description = "요양사 상세 정보를 조회합니다.")
    @GetMapping("/{workerConditionId}")
    public ResponseEntity<?> getWorkDetail(@PathVariable Long workerConditionId) {
      WorkerDetailResponse response = workerService.getWorkerDetail(workerConditionId);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 요양사 정보 수정
    @Operation(summary = "요양사 기본정보 편집", description = "요양사의 기본정보를 편집")
    @PatchMapping("/{workerId}")
    public ResponseEntity<?> updateWorker(@Valid @ModelAttribute WorkerUpdateRequest workerUpdate) {
        workerService.updateWorker(workerUpdate);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "요양사 정보 및 매칭 요청 알림 목록 조회", description = "요양사의 마이페이지 회원정보 및 매칭 요청 알림 목록을 조회")
    @GetMapping("/{workerId}/myPage")
    public ResponseEntity<?> getWorkerMyPage(@PathVariable("workerId") Long workerId) {
        return ResponseEntity.ok(workerService.getWorkerMyPage(workerId));
    }
}
