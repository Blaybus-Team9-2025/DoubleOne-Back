package org.doubleone.domain.manager.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doubleone.domain.manager.entity.Manager;
import org.doubleone.domain.manager.repository.ManagerRepository;
import org.doubleone.domain.senior.dto.SeniorCareDto;
import org.doubleone.domain.senior.entity.Senior;
import org.doubleone.domain.senior.repository.SeniorRepository;
import org.doubleone.domain.senior.service.SeniorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Manager")
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {

    private final SeniorService seniorService;
    private final SeniorRepository seniorRepository;
    private final ManagerRepository managerRepository;

    //노인정보 등록(임시)
    @PostMapping("/{managerId}/saveSenior")
    public ResponseEntity<Senior> saveSenior(@PathVariable Long managerId, @RequestBody Senior senior)
    {
        Manager user = managerRepository.findById(managerId)
                .orElseThrow(()->new RuntimeException("ManagerID not founded"));
        seniorService.saveSenior(senior, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //특정 노인의 요양사 구인정보 등록
    @PostMapping("/{seniorId}/saveSeniorCare")
    public ResponseEntity<SeniorCareDto> saveCare(@PathVariable Long seniorId, @RequestBody SeniorCareDto seniorCareDto)
    {
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(()->new RuntimeException("SeniorId not founded"));
        seniorService.saveSeniorCare(seniorCareDto, senior);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
