package br.com.actionlabs.carboncalc.rest;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.exception.ErrorResponse; // Importa a classe ErrorResponse
import br.com.actionlabs.carboncalc.utils.VerifyClassDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/open")
@RequiredArgsConstructor
@Slf4j
public class OpenRestController {

    @PostMapping("start-calc")
    public ResponseEntity<?> startCalculation(
        @RequestBody StartCalcRequestDTO request) {
        
        try {
            VerifyClassDto.verify(request, null);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("Validation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", Collections.singletonList(e.getMessage())));
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("An error occurred", Collections.singletonList(e.getMessage())));
        }

        return ResponseEntity.ok("Calculation started successfully."); 
    }

    @PutMapping("info")
    public ResponseEntity<?> updateInfo(
        @RequestBody UpdateCalcInfoRequestDTO request) {

        try {
            VerifyClassDto.verify(request, null);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("Validation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", Collections.singletonList(e.getMessage())));
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("An error occurred", Collections.singletonList(e.getMessage())));
        }

        throw new RuntimeException("Not implemented");
    }

    @GetMapping("result/{id}")
    public ResponseEntity<CarbonCalculationResultDTO> getResult(@PathVariable String id) {
        throw new RuntimeException("Not implemented");
    }
}
