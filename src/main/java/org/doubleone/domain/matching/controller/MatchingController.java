package org.doubleone.domain.matching.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.matching.dto.request.MatchingRequestDto;
import org.doubleone.domain.matching.dto.request.MatchingUpdateRequestDto;
import org.doubleone.domain.matching.dto.request.WorkerMatchingScheduleRequestDto;
import org.doubleone.domain.matching.entity.MatchingStatus;
import org.doubleone.domain.matching.entity.RunningStatus;
import org.doubleone.domain.matching.service.MatchingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Matching")
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {

  private final MatchingService matchingService;

  @Operation(summary = "매칭 요청", description = "관리자가 요양사에게 매칭 요청")
  @PostMapping
  public ResponseEntity<Long> createMatchingRequest(@RequestBody @Valid MatchingRequestDto requestDto) {
    Long matchingId = matchingService.createMatchingRequest(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(matchingId);
  }

  @Operation(summary = "매칭 상태 변경", description = "관리자 혹은 요양사의 매칭 상태 변경")
  @PatchMapping("/{matchingId}")
  public ResponseEntity<?> updateMatching(@PathVariable("matchingId") Long matchingId, @RequestBody @Valid MatchingUpdateRequestDto requestDto) {
    matchingService.updateMatching(matchingId, requestDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Operation(summary = "매칭 기록 목록 조회", description = "요양사의 매칭 기록 목록 조회")
  @GetMapping("/{workerId}")
  public ResponseEntity<?> getMatchingList(@PathVariable("workerId") Long workerId) {
    return ResponseEntity.status(HttpStatus.OK).body(matchingService.getMatchingList(workerId));
  }

  @Operation(summary = "요양사 매칭 일정 등록", description = "요양사의 매칭 일정 추가")
  @PostMapping("/{workerId}/schedules")
  public ResponseEntity<?> createMatchingSchedule(@PathVariable("workerId") Long workerId, @RequestBody @Valid WorkerMatchingScheduleRequestDto requestDto) {
    matchingService.createMatchingSchedule(workerId, requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


  @Operation(summary = "요양사 매칭 일정 조회", description = "요양사의 매칭 일정 및 시간표 조회")
  @GetMapping("/{workerId}/schedules")
  public ResponseEntity<?> getMatchingSchedule(@PathVariable("workerId") Long workerId) {
    return ResponseEntity.status(HttpStatus.OK).body(matchingService.getMatchingSchedule(workerId));
  }

  @Operation(summary = "매칭 현황 조회 (관리자 대시보드)", description = "매칭 현황 조회 (메인홈, 관리자 대시보드)")
  @GetMapping("/{managerId}/stat")
  public ResponseEntity<?> getMatchingStat(@PathVariable("managerId") Long managerId) {
    return ResponseEntity.status(HttpStatus.OK).body(matchingService.getMatchingStat(managerId));
  }

  @Operation(summary = "매칭 상태 목록 조회", description = "매칭 상태에 따른 어르신 목록 조회")
  @GetMapping("/{managerId}/seniors")
  public ResponseEntity<?> getMatchingSeniorList(@PathVariable("managerId") Long managerId, @RequestParam RunningStatus status) {
    return ResponseEntity.status(HttpStatus.OK).body(matchingService.getMatchingSeniorList(managerId, status));
  }

}
