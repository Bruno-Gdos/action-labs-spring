package br.com.actionlabs.carboncalc.rest;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.exception.ErrorResponse; 
import br.com.actionlabs.carboncalc.service.CalculationInfoService;
import br.com.actionlabs.carboncalc.service.CalculationResultService;
import br.com.actionlabs.carboncalc.service.GetMyCalculationsService;
import br.com.actionlabs.carboncalc.service.StartCalculationService;
import br.com.actionlabs.carboncalc.utils.Validator;
import br.com.actionlabs.carboncalc.utils.VerifyClassDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/open")
@Slf4j
public class OpenRestController {
  
    @Autowired
    private StartCalculationService startCalcService;

    @Autowired
    private CalculationInfoService calcInfoService;

    @Autowired
    private GetMyCalculationsService getMyCalculationsService;

    @Autowired
    CalculationResultService calculationResultService;
  

    @PostMapping("start-calc")
    public ResponseEntity<?> startCalculation(
        @RequestBody StartCalcRequestDTO request) {
        
        try {
            VerifyClassDto.verify(request, null);
            Validator.validateUF(request.getUf());
            Validator.validatePhoneNumber(request.getPhoneNumber());
            Validator.validateEmail(request.getEmail());

            StartCalcResponseDTO response = startCalcService.startCalculation(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("Validation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", Collections.singletonList(e.getMessage())));
            
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("An error occurred", Collections.singletonList(e.getMessage())));
        }
     }
    

    @PutMapping("info")
    public ResponseEntity<?> updateInfo(
        @RequestBody UpdateCalculationInfoRequestDTO request) {

        try {
            VerifyClassDto.verify(request, null);
            Validator.validateRecyclePercentage(request.getRecyclePercentage());

            UpdateCalcInfoResponseDTO response = calcInfoService.updateCalcInfo(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("Validation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", Collections.singletonList(e.getMessage())));
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("An error occurred", Collections.singletonList(e.getMessage())));
        }
    }

    @GetMapping("result/{id}")
    public ResponseEntity<?> getResult(@PathVariable String id) {
        try {
            CarbonCalculationResultDTO result = calculationResultService.calculateCarbonEmission(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("An error occurred", Collections.singletonList(e.getMessage())));
        }
    }

    @GetMapping("my-calcs/{email}")
    public ResponseEntity<?> getMyCalcs(@PathVariable String email) {
        try{
            GetMyCalculationsResultDTO result = getMyCalculationsService.getMyCalculations(email);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("An error occurred", Collections.singletonList(e.getMessage())));
        }
    }
}
