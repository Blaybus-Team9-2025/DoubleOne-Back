package org.doubleone.domain.worker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.worker.dto.WorkerDetailResponse;
import org.doubleone.domain.worker.dto.WorkerUpdateRequest;
import org.doubleone.domain.worker.service.WorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Operation(summary = "요양사 정보 수정", description = "요양사 정보를 수정합니다")
    @PutMapping("/{workerId}")
    public ResponseEntity<String> updateWorker(@PathVariable Long workerId,
        @RequestBody WorkerUpdateRequest request) {
        workerService.updateWorker(workerId, request);
        return ResponseEntity.ok("요양보호사 정보가 성공적으로 수정되었습니다.");
    }

    // 요양사 상세 정보 조회
    @Operation(summary = "요양사 상세 정보 조회", description = "요양사 상세 정보를 조회합니다.")
    @GetMapping("/{workerId}")
    public ResponseEntity<WorkerDetailResponse> getWorkDetail(@PathVariable Long workerId) {
        WorkerDetailResponse response = workerService.getWorkerDetail(workerId);
        return ResponseEntity.ok(response);
    }
}
