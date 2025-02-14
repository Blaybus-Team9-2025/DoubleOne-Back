package org.doubleone.domain.manager.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.dto.ManagerUpdateRequestDto;
import org.doubleone.domain.manager.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Manager")
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody ManagerUpdateRequestDto requestDto) {
        managerService.updateProfile(requestDto);
        return ResponseEntity.noContent().build();
    }
}
