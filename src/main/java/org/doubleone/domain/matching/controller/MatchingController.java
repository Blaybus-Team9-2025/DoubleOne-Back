package org.doubleone.domain.matching.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.matching.dto.request.MatchingRequestDto;
import org.doubleone.domain.matching.dto.request.MatchingUpdateRequestDto;
import org.doubleone.domain.matching.service.MatchingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<?> createMatchingRequest(@RequestBody @Valid MatchingRequestDto requestDto) {
    matchingService.createMatchingRequest(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "매칭 상태 변경", description = "관리자 혹은 요양사의 매칭 상태 변경")
  @PatchMapping("/{matchingId}")
  public ResponseEntity<?> updateMatching(@PathVariable("matchingId") Long matchingId, @RequestBody @Valid MatchingUpdateRequestDto requestDto) {
    matchingService.updateMatching(matchingId, requestDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
